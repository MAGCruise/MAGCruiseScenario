(define *exp-subject-id* "01")
(define *exp-settings-id* "1a1")

(define (def:game-scenario)
  (def:player 'JapaneseBridger 'human)

  (def:rounds 10
    (def:stage 'bridge
      (def:task 'JapaneseBridger 'japanese-bridger-model))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (japanese-bridger-model context ::Context self ::Player)

    (if (= context:roundnum 0)
            (ui:show-message 'JapaneseBridger *instruction-message*))

    (ui:show-message 'JapaneseBridger 
        (to-string "原文<strong>" context:roundnum "</strong>の修正をはじめて下さい．"))
        
    (let* ((answer-orig-text (*answer-orig-texts*:get context:roundnum))
           (question-orig-text (*question-orig-texts*:get context:roundnum))
           (prev-revised-text answer-orig-text))
         (japanese-bridger-behavior self context question-orig-text answer-orig-text prev-revised-text)))



(define (japanese-bridger-behavior self ::Player context ::Context question-orig-text answer-orig-text prev-revised-text)

  (define (translation src_lang target_lang src_text)
    (langrid:TranslationWithTemporalDictionary-translate 
      "TranslationCombinedWithBilingualDictionaryWithLongestMatchSearch" src_lang target_lang src_text
      (ArrayUtil:array Translation)
      target_lang
      '("TranslationPL" "KyotoUJServer")
      '("BilingualDictionaryWithLongestMatchSearchPL" "http://robin.ai.soc.i.kyoto-u.ac.jp/ymccommunitydictionary/services/YMCCommunityDictionary")))

    (ui:request-input self:name
      (make Form (to-string  "<br>質問文"
                        (number->string context:roundnum)
                        "<br><pre>" question-orig-text "</pre>"
                        "<br>原文"
                        (number->string context:roundnum)
                        "<br><pre>" answer-orig-text "</pre>")
            (make TextInput "<p><strong>上の文章を翻訳しやすい日本語文に修正して下さい．</strong></p>修正した日本語文" 'revised-sentence prev-revised-text))
      (lambda (revised-sentence)
        (self:set 'revised-sentence revised-sentence)))
    (let* ((translated-sentence (translation "ja" "en" (self:get 'revised-sentence)))
           (back-translated-sentence (translation "en" "ja" translated-sentence)))
        (ui:request-input self:name
            (make Form 
              (to-string 
                  "質問文" (number->string context:roundnum) 
                  "は以下です．<br><pre>" question-orig-text "</pre>"
                  "原文" (number->string context:roundnum) 
                  "は以下です．<br><pre>" answer-orig-text "</pre>"
                  "修正した文章は以下です．<br><pre>" (self:get 'revised-sentence) "</pre>"
                  "翻訳(日→英)の結果は以下です．<br><pre>" translated-sentence "</pre>"
                  "折り返し翻訳(日→英→日)の結果は以下です．<br><pre>" back-translated-sentence "</pre>")
              (make RadioInput 
                      "<strong>これで日本語の修正を終えますか？</strong>"
                      'again-or-finish "AGAIN" (list "再修正" "修正完了") (list "AGAIN" "FINISH")))
          (lambda (again-or-finish)
            (self:set 'again-or-finish again-or-finish)))
      (ui:show-message 'JapaneseBridger
          (to-string "<br><strong>・質問文" context:roundnum "</strong><br>　" question-orig-text 
                     "<br><strong>・原文" context:roundnum "</strong><br>　" answer-orig-text 
                     "<br><strong>・修正した文</strong><br>" (self:get 'revised-sentence)
                     "<br><strong>・翻訳(日→英)の結果</strong><br>　" translated-sentence
                     "<br><strong>・折り返し翻訳(日→英→日)の結果</strong><br>　" back-translated-sentence)))

        (if (equal? (self:get 'again-or-finish) "FINISH")
            (cond
               ((equal? (*thanks-message-timings*:get context:roundnum) #f) (ui:show-message 'JapaneseBridger *thanks-message*))
               ((equal? (*failure-message-timings*:get context:roundnum) #t) (ui:show-message 'JapaneseBridger *failure-message*)))
            (japanese-bridger-behavior self context 
                question-orig-text answer-orig-text (self:get 'revised-sentence))))

(define *thanks-message* "<div class=\"notification_of_exp\"><strong>あなたの修正により，ベトナムの児童に正しく情報が伝わりました．ありがとうございました．</strong></div>")
(define *failure-message* "<div class=\"notification_of_exp\"><strong>あなたが日本語文を修正してくれましたが，翻訳は改善されませんでした．</strong></div>")
(define *instruction-message* "<div class=\"notification_of_exp\"><strong>Bridgerであるあなたの日本文の書き換えサービスによって、翻訳文の品質が改善されます。一定の知識が伝わった場合、知識伝達がうまくいったことのお知らせや、作業に対する感謝のメッセージが、届くことがあります。</strong>")
(define *caution-message-to-faci* (string-append "<div class=\"notification_of_exp\"><strong>被験者IDと実験設定IDを確認して下さい．<br>" "subject-id=" *exp-subject-id* ", settings-id=" *exp-settings-id* "</strong>"))

(define *question-orig-texts*
  (list
  "乾田直播とは何ですか？" ;;00
  "菌はお米に害を与えますか？" ;;01
  "玄米につやがあり、白く濁っています。これはどのような玄米ですか？" ;;02
  "コブメイガを防除したいです。いつ農薬をまけばよいですか？" ;;03
  "害獣について教えてください。イネの根に被害を与える動物はなんですか？" ;;04
  "イネ紋枯れ病について教えてください。何を使えばよいですか？" ;;05
  "籾を穂から取ります。いつすればよいですか？" ;;06
  "有機肥料について教えてください。なぜ雑草を防除できるのですか？" ;;07
  "刈り取った稲を乾かします。どれくらいの時間乾かせばよいですか？" ;;08
  "稲の病気で困っています。いつごろ発生しやすいのでしょうか？" ;;09
  "殺虫剤にはどのようなものがありますか？" ;;10
  ))


(define *answer-orig-texts* 
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

(define *thanks-message-timings* 
  (list
    #f ;;00
    #f ;;01
    #f ;;02
    #f ;;03
    #f ;;04
    #f ;;05
    #f ;;06
    #t ;;07
    #t ;;08
    #f ;;09
    #t ;;10
  ))

(define *failure-message-timings*
  (list
    #f ;;00
    #f ;;01
    #f ;;02
    #f ;;03
    #f ;;04
    #f ;;05
    #f ;;06
    #f ;;07
    #f ;;08
    #t ;;09
    #f ;;10
  ))

