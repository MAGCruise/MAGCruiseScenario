package org.magcruise.gaming.examples.trans_srv.actor2017;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.magcruise.gaming.model.game.HistoricalField;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.ui.model.FormBuilder;
import org.magcruise.gaming.ui.model.attr.Max;
import org.magcruise.gaming.ui.model.attr.Min;
import org.magcruise.gaming.ui.model.attr.Required;
import org.magcruise.gaming.ui.model.input.NumberInput;
import org.nkjmlab.util.lang.MessageUtils;
import org.nkjmlab.util.lang.ResourceUtils;

public class TranslationServiceGamePlayer extends Player {

	@HistoricalField(name = "寄付/投資額")
	public volatile int investment = 0;

	@HistoricalField(name = "利用権")
	public volatile int rightOfUse = 0;

	@HistoricalField(name = "利益")
	public volatile int profit = 0;

	@HistoricalField(name = "口座残高")
	public volatile int account = 0;

	@HistoricalField(name = "寄付/投資額合計", visible = false)
	public int sumOfInvestment = 0;

	@HistoricalField(name = "バッジ", visible = false)
	private Set<Badge> badges = ConcurrentHashMap.newKeySet();

	private int income = 100;
	private double levelThreshold = 19.0;

	public TranslationServiceGamePlayer(PlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void initialize(TranslationServiceGameContext ctx) {
		showMessage("ゲームを開始します.");
		String css = String.join("", ResourceUtils.readAllLines(getClass(), "trans-srv.css"));
		sendStyleTag(css);
		String header = String.join("", ResourceUtils.readAllLines(getClass(), "header.html"));
		appendHtml("#row-bottom", header);
	}

	public void beforeRound(TranslationServiceGameContext ctx) {
		showMessage("ラウンド{}の開始です．", ctx.getRoundnum());

		if (ctx.getRoundnum() != 0) {
			updateHtml(ctx);
		}
		showMessage("ラウンド{}では，次の文章を翻訳します: <br>{}", ctx.getRoundnum(),
				getScentence(ctx.getRoundnum()));
	}

	private void updateHtml(TranslationServiceGameContext ctx) {
		setHtml("#div-history", tabulateRangeOfHistory(1, getHistorySize()));
		setHtml("#div-ranking", ctx.getRankingHtml());
		setHtml("#div-status", getStatusHtml());
		setHtml("#div-badges", getBadgesHtml());
	}

	public void finishGame(TranslationServiceGameContext ctx) {
		updateHtml(ctx);
		showMessage("ゲームが終わりました．お疲れ様でした．");
	}

	private String getStatusHtml() {
		return createLevelHtml() + createProgressBarHtml();

	}

	private String createLevelHtml() {
		return MessageUtils.format("<h3>Level: {}</h3>", getLevel());
	}

	public int getLevel() {
		return (int) (sumOfInvestment / levelThreshold);
	}

	private double getProgressPercentage() {
		return (sumOfInvestment % levelThreshold) * 100 / levelThreshold;
	}

	private String createProgressBarHtml() {
		return MessageUtils.format("<div class='progress'>"
				+ "<div class='progress-bar' style='width: {}%;'></div></div>",
				getProgressPercentage());
	}

	private String getBadgesHtml() {
		return String.join(" ",
				badges.stream().sorted(Comparator.comparing(b -> b.toString()))
						.map(b -> b.getHtml()).collect(Collectors.toList()));
	}

	public void afterRound(TranslationServiceGameContext ctx) {
		sumOfInvestment += investment;
		profit = income - investment + rightOfUse;
		account += profit;
		showMessagesOfRound(ctx);
		updateBadges(ctx);
	}

	private void updateBadges(TranslationServiceGameContext ctx) {
		Set<Badge> tmp = new HashSet<>();
		{
			int level = getLevel();
			if (level >= 5) {
				tmp.add(Badge.LEVEL_05);
			}
			if (level >= 10) {
				tmp.add(Badge.LEVEL_10);
			}
			if (level >= 25) {
				tmp.add(Badge.LEVEL_25);
			}
			if (level >= 50) {
				tmp.add(Badge.LEVEL_50);
			}
		}
		{
			if (investment >= 35) {
				tmp.add(Badge.INVESTMENT_1_MOUSE);
			}
			if (investment >= 60) {
				tmp.add(Badge.INVESTMENT_2_CAT);
			}
			if (investment >= 80) {
				tmp.add(Badge.INVESTMENT_3_DOG);
			}
			if (investment >= 100) {
				tmp.add(Badge.INVESTMENT_4_LION);
			}
		}
		{
			if (isOverKeywordInvestment(ctx.getRoundnum())) {
				tmp.add(getKeywordBadge(ctx.getRoundnum()));
			}
		}

		tmp.removeAll(badges);
		if (tmp.size() != 0) {
			showAlert("バッジを獲得しました",
					String.join(" ",
							tmp.stream().map(b -> b.getHtml()).collect(Collectors.toList())),
					"");
			badges.addAll(tmp);
		}
	}

	private Badge getKeywordBadge(int roundnum) {
		Badge[] badges = { Badge.KEYWORD_01_MOLD, Badge.KEYWORD_02_RICE, Badge.KEYWORD_03_FLY,
				Badge.KEYWORD_04_WILD_PIG, Badge.KEYWORD_05_CHEMICAL, Badge.KEYWORD_06_HUSK,
				Badge.KEYWORD_07_FERTILIZER, Badge.KEYWORD_08_DRY, Badge.KEYWORD_09_RICE_PLANT,
				Badge.KEYWORD_10_BUG_KILLER };
		return badges[roundnum - 1];
	}

	private boolean isOverKeywordInvestment(int roundnum) {
		int[] thresholds = { 20, 20, 30, 30, 50, 30, 30, 50, 50, 80 };
		if (roundnum <= thresholds.length) {
			return investment >= thresholds[roundnum - 1];
		}
		return false;
	}

	private void showMessagesOfRound(TranslationServiceGameContext ctx) {
		showMessage("ラウンド{}で受けとった翻訳文 : <br>{}", ctx.getRoundnum(),
				getTranslatedScentence(ctx.getRoundnum()));
		showMessage("ラウンド{}では，{}トークンを寄付/投資し，{}トークンの利用権を得ました．このラウンドの利益は{}トークンです．", ctx.getRoundnum(),
				investment, rightOfUse, profit);
	}

	public void decide(TranslationServiceGameContext ctx) {
		FormBuilder builder = new FormBuilder(
				"ラウンド" + ctx.getRoundnum() + ": 寄付/投資額をいくらにしますか？<br>");
		builder.addLabel("このラウンドで寄付/投資できるトークン数は，0以上" + income + "以下の任意の整数です．");
		builder.addInput(new NumberInput("寄付/投資額(トークン)", "token", income,
				new Min(0), new Max(income), new Required()));
		syncRequestToInput(builder.build(), params -> {
			this.investment = params.getInt("token");
		});
	}

	private String getScentence(int roundnum) {
		return getText(sentences, roundnum);
	}

	private String getText(List<String> sentences, int roundnum) {
		if (roundnum == 0) {
			return sentences.get(0);
		}
		return sentences.get((roundnum - 1) % 10);
	}

	private String getTranslatedScentence(int roundnum) {
		return getText(translatedSentences, roundnum);
	}

	public synchronized int getAccount() {
		return account;
	}

	private List<String> sentences = Arrays.asList(
			"菌には、いろいろな種類があります。稲に被害を及ぼす菌としては、いもち病菌や紋枯病菌などがあります。菌は、カビであり、簡単に増殖するので、適切な防除をしないと被害が大きくなります。",
			"玄米につやがあり、全体が白く濁っているのなら、モチ米ではないでしょうか。あるいは、玄米の中は透明で外側だけ白く濁っているのなら、乳白米と言うものであり、あまり品質は良くありません。",
			"コブノメイガは、葉が白くなりますが、暫くすると稲が回復します。コブノメイガの幼虫を見つけたら、すぐに農薬を散布することです。コブノメイガの幼虫が小さい時に農薬をまくのが効果的です。",
			"イネが踏み倒されているのは動物による被害です。イノシシは田んぼを踏み荒らしながら、イネに実ったお米を食べてしまいます。稲株の下の方が噛み切られているのなら、恐らくネズミの仕業でしょう。",
			"紋枯病専用の農薬があります。また、紋枯病は、カビの病気ですから、密植にして稲の株の間の湿度が高くならないように注意することが大事です。少し疎植にして、稲株の間に風が通るようにすると良いでしょう。",
			"それを脱穀といいますが、穂全体の籾が黄色になってから脱穀します。刈取して直ぐにではなく、少し穂を乾燥させてからの方が良いですね。イネを刈って乾燥させたら、イネの穂からもみを外す脱穀をして下さい。",
			"有機肥料には水分中の酸素を消費する性質があります。この性質を利用して、有機肥料を田んぼに投入し水に溶けている酸素を少なくすることができます。そうすることで、水を深くしなくても湿性雑草の種子発芽を抑制できます。",
			"収穫後の稲を乾燥させる期間は、その場所の状況によって異なります。ゆっくり、無理なく乾燥させるなら2週間ほどかかりましょうか。籾の水分15%が理想的です。刈り取った稲は、棒などにかけ2週間、天日と風で乾燥させます。",
			"稲の病気は小さな苗の時から、生育に従っていろいろな病気が出ます。穂が出る頃、また実る頃出る病気もあります。例えばイモチ病は風通しが悪く湿度が高いと出やすいです．",
			"縞はがれ病は6月下旬から9月下旬にかけて発生します。",
			"今、ベトナムで市販されている殺虫剤の種類は分かりませんが、害虫の種類によって使う殺虫剤は異なります。殺虫剤の種類には、殺虫剤を虫に直接かけるもの、植物に直接かけるもの、土にまくもの、そして虫が好きな餌に混ぜるものがあります。");

	private List<String> translatedSentences = Arrays.asList(
			"Có nhiều loại vi khuẩn. Có nấm gây bệnh nấm lúa, phát hiện ra vi khuẩn gây bệnh rám nắng ... như là vi khuẩn gây hại cho gạo. Nấm là mốc và phát triển dễ dàng, vì vậy nếu bạn không kiểm soát đúng thiệt hại sẽ tăng lên.",
			"Nếu gạo nâu có bóng và toàn bộ là màu trắng sữa, không phải là gạo cơm? Ngoài ra, nếu bên trong gạo nâu là trong suốt và chỉ có màu trắng ở bên ngoài là bùn, nó được gọi là gạo trắng sữa, và chất lượng không phải là rất tốt.",
			"Kobuno meiga, lá biến thành màu trắng, nhưng sau một thời gian gạo sẽ phục hồi. Một khi bạn đã tìm thấy một ấu trùng của Cobno meiga, phun thuốc trừ sâu ngay lập tức. Điều này có hiệu quả khi đưa các hóa chất nông nghiệp khi ấu trùng của Coleoptera là nhỏ.",
			"Đó là thiệt hại của động vật mà gạo đang bị giẫm đạp. Trong khi đi lên cánh đồng lúa, chú heo ăn gạo cơm. Nếu phần dưới của cây lúa bị cắn, có thể đó là công việc của chuột.",
			"Có thuốc trừ sâu độc hại cho vết đen. Ngoài ra, nó là một bệnh của nấm mốc, vì vậy điều quan trọng là phải chú ý để độ ẩm giữa các kho gạo không trở thành cao bởi trồng dày đặc. Có thể hơi thưa thớt để gió vượt qua các kho gạo.",
			"Nó được gọi là đập lúa, nhưng đập sau khi tinh thần của toàn bộ chảo trở nên vàng. Không nên cắt ngay lập tức mà là để khô tai một chút. Sau khi cắt và sấy gạo, vui lòng nhổ kỹ kỹ lưỡng loại bỏ gạo khỏi hạt gạo.",
			"Phân hữu cơ có đặc tính tiêu hao ôxy trong độ ẩm. Bằng cách sử dụng tài sản này, bạn có thể phun phân bón hữu cơ vào ruộng lúa và làm giảm oxy hòa tan trong nước. Bằng cách đó, bạn có thể kiểm soát nảy mầm hạt cỏ dại ẩm ướt mà không làm sâu nước.",
			"Thời gian sấy lúa thu hoạch phụ thuộc vào tình hình tại nơi đó. Phải mất khoảng 2 tuần để khô một cách chậm chạp và hợp lý. Lý tưởng cho độ ẩm của lúa 15%. Gạo thu hoạch được phơi nắng và gió trong 2 tuần trên cây gậy và các loại tương tự.",
			"Từ thời điểm cây con nhỏ, bệnh của cây lúa xuất phát từ các bệnh khác nhau theo tăng trưởng. Có nhiều loại bệnh xuất hiện sớm nhất kể từ khi đột biến trở lại. Ví dụ, bệnh Immushi dễ đi ra ngoài với thông gió kém và độ ẩm cao. Dải bắt đầu từ cuối tháng 6 đến cuối tháng 9.",
			"Tôi không biết loại thuốc trừ sâu được bán ở Việt Nam hiện nay, nhưng loại thuốc trừ sâu được sử dụng phụ thuộc vào loại sâu bệnh. Có nhiều loại thuốc trừ sâu, những loại thuốc trừ sâu trực tiếp cho côn trùng, những loại này áp dụng trực tiếp cho cây cối, những người gieo đất, và những thứ khác kết hợp với côn trùng như mồi.");

	public enum Badge {
		KEYWORD_01_MOLD(
				"https://i.gyazo.com/a015363f35cdd4f29fd770bcf0437b65.png"), KEYWORD_02_RICE(
						"https://i.gyazo.com/b6ed900bd8dae774477e4841d9690665.png"), KEYWORD_03_FLY(
								"https://i.gyazo.com/b53e007d6190695243894c9cc6f8068f.png"), KEYWORD_04_WILD_PIG(
										"https://i.gyazo.com/21a21005f63a522c0eeadcb801fb1484.png"), KEYWORD_05_CHEMICAL(
												"https://i.gyazo.com/837b080a54164aaa55d7083ab051fee6.png"), KEYWORD_06_HUSK(
														"https://i.gyazo.com/3375fd0c3df7b4a5b47f1523b13683b4.png"), KEYWORD_07_FERTILIZER(
																"https://i.gyazo.com/1966074e6fc262cca688bc7aa9e761ef.png"), KEYWORD_08_DRY(
																		"https://i.gyazo.com/fbd0f4ea15149703bbb0c8d9fef8abdb.png"), KEYWORD_09_RICE_PLANT(
																				"https://i.gyazo.com/c957aaa3686a9935b8c84ac80aeb29e9.png"), KEYWORD_10_BUG_KILLER(
																						"https://i.gyazo.com/4feb11e3b191a4189ffa768ace5abfa2.png"), INVESTMENT_4_LION(
																								"https://i.gyazo.com/6254f74935695f4b0cc4820089e0f06a.png"), INVESTMENT_3_DOG(
																										"https://i.gyazo.com/1dfcd5baad9cded841c6d08e1fa6c548.png"), INVESTMENT_2_CAT(
																												"https://i.gyazo.com/7613444cc7774040a8c7f2563b91fd36.png"), INVESTMENT_1_MOUSE(
																														"https://i.gyazo.com/21ce8538f665051770a9751f1918938e.png"), LEVEL_50(
																																"https://i.gyazo.com/edc2b3a7ac46ec2bca133d627696412f.png"), LEVEL_25(
																																		"https://i.gyazo.com/12078984cfd787b164274f444c15a4f3.png"), LEVEL_10(
																																				"https://i.gyazo.com/ffb0888e1140ec966a1237b4681d8c8f.png"), LEVEL_05(
																																						"https://i.gyazo.com/48b733ac78d98b55f5badb4a2e6cdea1.png");
		private String href;

		Badge(String href) {
			this.href = href;
		}

		public String getHtml() {
			return "<img class='exp-badge' src='" + getHref() + "'>";
		}

		String getHref() {
			return href;
		}
	}

}
