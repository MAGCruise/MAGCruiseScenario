package org.magcruise.gaming.examples.trans_srv.actor;

import java.util.Arrays;
import java.util.List;

import org.magcruise.gaming.examples.trans_srv.resource.TranslationServiceGameResourceLoader;
import org.magcruise.gaming.langrid.AccessConfigFactory;
import org.magcruise.gaming.langrid.client.TranslationClient;
import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.model.game.HistoricalField;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.ui.model.FormBuilder;
import org.magcruise.gaming.ui.model.attr.Max;
import org.magcruise.gaming.ui.model.attr.Min;
import org.magcruise.gaming.ui.model.attr.Required;
import org.magcruise.gaming.ui.model.input.NumberInput;
import org.magcruise.gaming.ui.model.input.RadioInput;

public class TranslationServiceGamePlayer extends Player {

	public static void main(String[] args) {
		AccessConfigFactory.setPath(new TranslationServiceGameResourceLoader()
				.getResource("langrid-conf.json"));
		TranslationClient client = new TranslationClient("KyotoUJServer");

		log.debug(client.translate("ja", "ko", "こんにちは"));

	}

	@HistoricalField(name = "口座(トークン)")
	public volatile int account = 0;

	@HistoricalField(name = "寄付金(トークン)")
	public volatile int investment = 0;

	@HistoricalField(name = "利用権(トークン)")
	public volatile int rightOfUse = 0;

	@HistoricalField(name = "スコア(点)", visible = false)
	public volatile int score = 0;

	@HistoricalField(name = "スコア(合計点)", visible = false)
	public volatile int sumOfScore = 0;

	public TranslationServiceGamePlayer(PlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void beforeRound(TranslationServiceGameContext ctx) {
		account += 100;
		investment = 0;
		rightOfUse = 0;
		showMessage("rd. " + ctx.getRoundnum() + " の開始です．");
		if (ctx.getRoundnum() != 0) {
			showMessage("前回までのラウンドのまとめです．" + tabulateHistory());
		}
		showMessage(
				"次の文章を翻訳します: <br>「" + getScentence(ctx.getRoundnum()) + "」");
	}

	public void afterRound(TranslationServiceGameContext ctx) {
		AccessConfigFactory.setPath(new TranslationServiceGameResourceLoader()
				.getResource("langrid-conf.json"));
		TranslationClient client = new TranslationClient("KyotoUJServer");

		String sentence;
		if (ctx.getRoundnum() <= 15) {
			sentence = client.translate("ja", "ko",
					getScentence(ctx.getRoundnum()));
		} else if (ctx.getRoundnum() <= 20) {
			if (ctx.funds < 200) {
				sentence = client.translate("ja", "zh",
						getScentence(ctx.getRoundnum()));
			} else if (ctx.funds < 300) {
				sentence = client.translate("ja", "en",
						getScentence(ctx.getRoundnum()));
			} else {
				sentence = client.translate("ja", "ko",
						getScentence(ctx.getRoundnum()));

			}
		} else {
			if (ctx.funds == 0) {
				sentence = getScentence(ctx.getRoundnum());
			} else if (ctx.funds < 200) {
				sentence = client.translate("ja", "zh",
						getScentence(ctx.getRoundnum()));
			} else if (ctx.funds < 300) {
				sentence = client.translate("ja", "en",
						getScentence(ctx.getRoundnum()));
			} else {
				sentence = client.translate("ja", "ko",
						getScentence(ctx.getRoundnum()));
			}
		}

		showMessagesOfRound(ctx.getRoundnum(), sentence);

		account += rightOfUse;
		account -= investment;
		sumOfScore += score;

	}

	public void initialize(TranslationServiceGameContext ctx) {
		showMessage("ゲームを開始します.");
	}

	// 最終的な結果を出力する
	public void end(TranslationServiceGameContext ctx) {
		if (sumOfScore <= 5) {
			account += 0;
		} else if (sumOfScore <= 10) {
			account += 1000;
		} else if (sumOfScore <= 20) {
			account += 5000;
		} else if (sumOfScore <= 30) {
			account += 10000;
		}
		showMessage("最終的な結果．" + tabulateHistory());
		showMessage("スコア :" + sumOfScore);
	}

	public void decide(TranslationServiceGameContext ctx) {

		FormBuilder builder = new FormBuilder(
				"ラウンド" + ctx.getRoundnum() + ": 寄付金をいくらにしますか？<br>");

		if (ctx.getRoundnum() <= 5) {
			builder.addLabel("このラウンドで寄付できるのは0トークンか，100トークンです．");
			builder.setInput(new RadioInput("寄付金(トークン)", "token", "0",
					Arrays.asList(new String[] { "0トークン", "100トークン" }),
					Arrays.asList(new String[] { "0", "100" }),
					new Required()));
			syncRequestToInput(builder.build(), params -> {
				this.investment = params.getInt("token");
			});
		} else if (ctx.getRoundnum() <= 25) {
			builder.addLabel("このラウンドで寄付できトークン数は，0以上100以下の任意の整数です．");
			builder.setInput(new NumberInput("寄付金(トークン)", "token", 100,
					new Min(0), new Max(100), new Required()));
			syncRequestToInput(builder.build(), params -> {
				this.investment = params.getInt("token");
			});
		}
	}

	private String getScentence(int roundnum) {
		if (roundnum == 0) {
			return sentences.get(0);
		}
		return sentences.get((roundnum - 1) % 10);
	}

	private void showMessagesOfRound(int roundnum, String sentence) {
		showMessage("今回のラウンドで寄付金として，" + investment + "トークンを寄付しました．");
		showMessage("今回のラウンドで利用権として，" + rightOfUse + "トークンを獲得しました．");
		showMessage("今回のラウンドで受けとった翻訳文 : <br>「" + sentence + "」");
	}

	private List<String> sentences = Arrays.asList(new String[] {
			"菌には、いろいろな種類があります。稲に被害を及ぼす菌としては、いもち病菌や紋枯病菌などがあります。菌は、カビであり、簡単に増殖するので、適切な防除をしないと被害が大きくなります。",
			"玄米につやがあり、全体が白く濁っているのなら、モチ米ではないでしょうか。あるいは、玄米の中は透明で外側だけ白く濁っているのなら、乳白米と言うものであり、あまり品質は良くありません。",
			"コブノメイガは、葉が白くなりますが、暫くすると稲が回復します。コブノメイガの幼虫を見つけたら、すぐに農薬を散布することです。コブノメイガの幼虫が小さい時に農薬をまくのが効果的です。",
			"イネが踏み倒されているのは動物による被害です。イノシシは田んぼを踏み荒らしながら、イネに実ったお米を食べてしまいます。稲株の下の方が噛み切られているのなら、恐らくネズミの仕業でしょう。",
			"紋枯病専用の農薬があります。また、紋枯病は、カビの病気ですから、密植にして稲の株の間の湿度が高くならないように注意することが大事です。少し疎植にして、稲株の間に風が通るようにすると良いでしょう。",
			"それを脱穀といいますが、穂全体の籾が黄色になってから脱穀します。刈取して直ぐにではなく、少し穂を乾燥させてからの方が良いですね。イネを刈って乾燥させたら、イネの穂からもみを外す脱穀をして下さい。",
			"有機肥料には水分中の酸素を消費する性質があります。この性質を利用して、有機肥料を田んぼに投入し水に溶けている酸素を少なくすることができます。そうすることで、水を深くしなくても湿性雑草の種子発芽を抑制できます。",
			"収穫後の稲を乾燥させる期間は、その場所の状況によって異なります。ゆっくり、無理なく乾燥させるなら2週間ほどかかりましょうか。籾の水分15％が理想的です。刈り取った稲は、棒などにかけ2週間、天日と風で乾燥させます。",
			"稲の病気は小さな苗の時から、生育に従っていろいろな病気が出ます。穂が出る頃、また実る頃出る病気もあります。例えばイモチ病は風通しが悪く湿度が高いと出やすいです．",
			"縞はがれ病は6月下旬から9月下旬にかけて発生します。",
			"今、ベトナムで市販されている殺虫剤の種類は分かりませんが、害虫の種類によって使う殺虫剤は異なります。殺虫剤の種類には、殺虫剤を虫に直接かけるもの、植物に直接かけるもの、土にまくもの、そして虫が好きな餌に混ぜるものがあります。" });

}
