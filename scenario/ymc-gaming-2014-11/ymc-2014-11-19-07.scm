(define *a-orig-texts* ::list
  (list 
  "乾田直播は、乾いた土に直に播種することです。播種後に、田んぼに水を入れます。これは別に、田んぼに水を入れておいて、水の上から播種したり、湿った状態の土に播種する方法もあります。" ;; 00
  "菌には、いろいろな種類があります。稲に被害を及ぼす菌としては、いもち病菌や紋枯病菌などがあります。菌は、カビであり、簡単に増殖するので、適切な防除をしないと被害が大きくなります。" ;;01
  "玄米につやがあり、全体が白く濁っているのなら、モチ米ではないでしょうか。あるいは、玄米の中は透明で外側だけ白く濁っているのなら、乳白米と言うものであり、あまり品質は良くありません。" ;;02
  "コブノメイガは、葉が白くなりますが、暫くすると稲が回復します。コブノメイガの幼虫を見つけたら、すぐに農薬を散布することです。コブノメイガの幼虫が小さい時に農薬をまくのが効果的です。" ;;03
  "イネが踏み倒されているのは動物による被害です。イノシシは田んぼを踏み荒らしながら、イネに実ったお米を食べてしまいます。稲株の下の方が噛み切られているのなら、恐らくネズミの仕業でしょう。" ;;04
  "紋枯病専用の農薬があります。また、紋枯病は、カビの病気ですから、密植にして稲の株の間の湿度が高くならないように注意することが大事です。少し疎植にして、稲株の間に風が通るようにすると良いでしょう。" ;;05
  "それを脱穀といいますが、穂全体の籾が黄色になってから脱穀します。刈取して直ぐにではなく、少し穂を乾燥させてからの方が良いですね。イネを刈って乾燥させたら、イネの穂からもみを外す脱穀をして下さい。" ;;06
  "有機肥料には水分中の酸素を消費する性質があります。この性質を利用して、有機肥料を田んぼに投入し水に溶けている酸素を少なくすることができます。そうすることで、水を深くしなくても湿性雑草の種子発芽を抑制できます。" ;;07
  "収穫後の稲を乾燥させる期間は、その場所の状況によって異なります。ゆっくり、無理なく乾燥させるなら2週間ほどかかりましょうか。籾の水分15％が理想的です。刈り取った稲は、棒などにかけ2週間、天日と風で乾燥させます。" ;;08
  "稲の病気は小さな苗の時から、生育に従っていろいろな病気が出ます。穂が出る頃、また実る頃出る病気もあります。例えばイモチ病は風通しが悪く湿度が高いと出やすいです．縞はがれ病は6月下旬から9月下旬にかけて発生します。" ;;09
  "今、ベトナムで市販されている殺虫剤の種類は分かりませんが、害虫の種類によって使う殺虫剤は異なります。殺虫剤の種類には、殺虫剤を虫に直接かけるもの、植物に直接かけるもの、土にまくもの、そして虫が好きな餌に混ぜるものがあります。" ;;10
))

(define *a-orig-scores* ::list
  (list "0.00" "1.11" "2.22" "3.33" "4.44" "5.55" "6.66" "7.77" "8.88" "9.99"))


(define-alias Bridger org.magcruise.gaming.scenario.ymc.incentive.Bridger)
(define-alias Evaluator org.magcruise.gaming.scenario.ymc.incentive.Evaluator)
(define-alias YMCContext org.magcruise.gaming.scenario.ymc.incentive.YMCContext)

(define-namespace bridger "bridger")
(define-namespace evaluator "evaluator")

(define (def:game-scenario)
  (def:ext-context YMCContext)
  (def:ext-player 'Bridger 'human Bridger)
  (def:ext-player 'Evaluator 'human Evaluator)

  (def:round
    (def:stage 'init
      (def:task 'Bridger 'bridger:init)
        (def:task 'Evaluator 'evaluator:init))
    (def:stage 'bridge-eval
      (def:task 'Bridger 'bridger:edit)))

  (def:rounds 2
    (def:stage 'receive-eval
      (def:task 'Bridger 'bridger:receive-eval))
    (def:restage 'bridge-eval)))

(define (bridger:init ctx ::YMCContext self ::Bridger)
  (ui:show-message self:name (<div-class> "notification_of_exp" "Bridgerであるあなたの日本文の書き換えサービスによって，翻訳文の品質が改善されます．")))

(define (evaluator:init ctx ::YMCContext self ::Evaluator)
  (ui:show-message self:name "評価する文章が到着するまでお待ち下さい．"))

(define (bridger:receive-eval ctx ::YMCContext self ::Bridger)
  (define evaluator ::Evaluator (ctx:getPlayer 'Evaluator))
  (ui:show-message self:name (to-string "原文の評価値は" (*a-orig-scores*:get ctx:roundnum) "でした．" "修正した文章の評価値は" evaluator:averageScore "になりました．")))

(define (bridger:edit ctx ::YMCContext self ::Bridger)
  (define a-orig-text (*a-orig-texts*:get ctx:roundnum))
  (define prev-revised-text a-orig-text)

  (ui:show-message self:name
    "原文" ctx:roundnum "の修正をはじめて下さい．")
  (bridger:edit-aux ctx self a-orig-text prev-revised-text))
  
(define (bridger:edit-aux ctx ::YMCContext self ::Bridger a-orig-text prev-revised-text)
  (ui:request-input self:name
    (make Form
      (<div>
        (<p> "原文" ctx:roundnum) (<blockquote> a-orig-text)
        (<p> "原文の評価値は" (<strong> (*a-orig-scores*:get ctx:roundnum)) "でした．"))
      (make TextInput "原文を分かりやすい日本語に修正して，入力して下さい．" 'revised-sentence prev-revised-text))
    (lambda (revised-sentence)
      (set! self:revisedSentence revised-sentence)
      (define ev ::Evaluator (ctx:getPlayer 'Evaluator))
      (evaluator:evaluate ctx ev)
      (bridger:receive-eval ctx self)))


  (define (make-msg)
    (<div>
      (<p> "原文" ctx:roundnum "は以下です．")(<blockquote> a-orig-text)
      (<p> "修正した文章は以下です．")(<blockquote> self:revisedSentence)))

  (ui:request-input self:name
    (make Form (make-msg)
      (make RadioInput 
          (<strong> "これで修正を終えますか？") 'again-or-finish "AGAIN" (list "再修正" "修正完了") (list "AGAIN" "FINISH")))
    (lambda (again-or-finish)
      (when (equal? again-or-finish "AGAIN")
        (bridger:edit-aux ctx self a-orig-text self:revisedSentence)))))

(define (evaluator:evaluate ctx ::YMCContext self ::Evaluator)
  (define bridger ::Bridger (ctx:getPlayer 'Bridger))
  (ui:request-input self:name
    (make Form (<p> bridger:revisedSentence)
      (make RadioInput 
          (<strong> "Aさん，この文章は分かりやすいですか？") '_scoreA "3" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5"))
      (make RadioInput 
          (<strong> "Bさん，この文章は分かりやすいですか？") '_scoreB "3" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5"))
      (make RadioInput 
          (<strong> "Cさん，この文章は分かりやすいですか？") '_scoreC "3" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5")))
    (lambda (_scoreA _scoreB _scoreC)
      (self:setAverageScore _scoreA _scoreB _scoreC)
      (ui:show-message self:name "評価する次の文章が到着するまでお待ち下さい．"))))
