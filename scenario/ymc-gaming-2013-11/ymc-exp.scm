(define *question-orig-texts* ::list
  (list
  "乾田直播とは何ですか？" ;;00
  "菌はお米に害を与えますか？" ;;01
  ))


(define *answer-orig-texts*  ::list
  (list
    "乾田直播は、乾いた土に直に播種することです。播種後に、田んぼに水を入れます。これは別に、田んぼに水を入れておいて、水の上から播種したり、湿った状態の土に播種する方法もあります。" ;; 00
    "菌には、いろいろな種類があります。稲に被害を及ぼす菌としては、いもち病菌や紋枯病菌などがあります。菌は、カビであり、簡単に増殖するので、適切な防除をしないと被害が大きくなります。" ;;01
  ))

(define *thanks-message-timings* ::list
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

(define *failure-message-timings* ::list
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


(define *exp-subject-id* "01")
(define *exp-settings-id* "1a1")

(define-alias Bridger org.magcruise.gaming.scenario.ymc.incentive.JPBridger)
(define-alias Evaluator org.magcruise.gaming.scenario.ymc.incentive.Evaluator)
(define-alias YMCContext org.magcruise.gaming.scenario.ymc.incentive.YMCContext)

(define-namespace jp-expert "jp-expert")
(define-namespace jp-bridger "jp-bridger")
(define-namespace vt-bridger "vt-bridger")
(define-namespace vt-youth "vt-youth")

(define (def:setup-game-builder game-builder ::GameBuilder)
  (def:context YMCContext)
  (def:player 'JP-Expert 'agent SimplePlayer)
  (def:player 'JP-Bridger 'human Bridger)
  (def:player 'VT-Bridger 'agent SimplePlayer)
  (def:player 'VT-Youth 'agent SimplePlayer)

  (def:rounds 10
    (def:stage 'question-answer
      (def:task 'VT-Youth 'vt-youth:question)
      (def:task 'JP-Expert 'jp-expert:answer)
      (def:task 'JP-Bridger 'jp-bridger:bridge-jp-en)
      (def:task 'VT-Bridger 'vt-bridger:bridge-en-vt))
    (def:stage 'feedback
      (def:task 'VT-Youth 'vt-youth:decide-feedback))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (jp-bridger:init ctx ::YMCContext self ::Bridger)
  (manager:show-message 'JP-Bridger *instruction-message*))

(define (vt-youth:question)
  (set! self:question (question-orig-text (*question-orig-texts*:get ctx:roundnum))))

(define (jp-expert:answer)
  (set! self:answer (answer-orig-text (*answer-orig-texts*:get ctx:roundnum))))

(define (jp-bridger:bridge-jp-en ctx ::YMCContext self ::Bridger)
    (manager:show-message 'JP-Bridger
        (to-string "原文<strong>" ctx:roundnum "</strong>の修正をはじめて下さい．"))
    (japanese-bridger-behavior self ctx question-orig-text answer-orig-text prev-revised-text))



(define (translation src_lang target_lang src_text)
  (define client ::TranslationWithTemporalDictionaryService
     (langrid:make-client TranslationWithTemporalDictionaryService
      "TranslationCombinedWithBilingualDictionaryWithLongestMatchSearch"
      (list
        '("TranslationPL" "KyotoUJServer")
        '("BilingualDictionaryWithLongestMatchSearchPL"
          "http://robin.ai.soc.i.kyoto-u.ac.jp/ymccommunitydictionary/services/YMCCommunityDictionary"))))
    (client:translate src_lang target_lang src_text (make Translation[]) target_lang))

(define (japanese-bridger-behavior self ::Player ctx ::Context question-orig-text answer-orig-text prev-revised-text)
    (self:syncRequestToInput 
      (make Form (to-string
                    "質問文"
                    (number->string ctx:roundnum)
                    "<br><pre>" question-orig-text "</pre>"
                    "<br>原文"
                    (number->string ctx:roundnum)
                    "<br><pre>" answer-orig-text "</pre>")
            (make TextInput "<p><strong>上の文章を翻訳しやすい日本語文に修正して下さい．</strong></p>修正した日本語文" 'revised-sentence prev-revised-text))
      (lambda (revised-sentence)
        (self:set 'revised-sentence revised-sentence)))

    (let* ((translated-sentence (translation "ja" "en" self:revisedSentence))
           (back-translated-sentence (translation "en" "ja" translated-sentence)))
        (self:syncRequestToInput 
            (make Form
              (to-string
                  "質問文" (number->string ctx:roundnum)
                  "は以下です．<br><pre>" question-orig-text "</pre>"
                  "原文" (number->string ctx:roundnum)
                  "は以下です．<br><pre>" answer-orig-text "</pre>"
                  "修正した文章は以下です．<br><pre>" (self:get 'revised-sentence) "</pre>"
                  "翻訳(日→英)の結果は以下です．<br><pre>" translated-sentence "</pre>"
                  "折り返し翻訳(日→英→日)の結果は以下です．<br><pre>" back-translated-sentence "</pre>")
              (make RadioInput
                      "<strong>これで日本語の修正を終えますか？</strong>"
                      'again-or-finish "AGAIN" (list "再修正" "修正完了") (list "AGAIN" "FINISH")))
          (lambda (again-or-finish)
            (self:set 'again-or-finish again-or-finish)))
      (manager:show-message 'JapaneseBridger
          (to-string "<br><strong>・質問文" ctx:roundnum "</strong><br>　" question-orig-text
                     "<br><strong>・原文" ctx:roundnum "</strong><br>　" answer-orig-text
                     "<br><strong>・修正した文</strong><br>" (self:get 'revised-sentence)
                     "<br><strong>・翻訳(日→英)の結果</strong><br>　" translated-sentence
                     "<br><strong>・折り返し翻訳(日→英→日)の結果</strong><br>　" back-translated-sentence)))

        (if (equal? (self:get 'again-or-finish) "FINISH")
            (cond
               ((equal? (*thanks-message-timings*:get ctx:roundnum) #f) (manager:show-message 'JP-Bridger *thanks-message*))
               ((equal? (*failure-message-timings*:get ctx:roundnum) #t) (manager:show-message 'JP-Bridger *failure-message*)))
            (japanese-bridger-behavior self ctx
                question-orig-text answer-orig-text (self:get 'revised-sentence))))

(define *thanks-message* "<div class=\"notification_of_exp\"><strong>あなたの修正により，ベトナムの児童に正しく情報が伝わりました．ありがとうございました．</strong></div>")
(define *failure-message* "<div class=\"notification_of_exp\"><strong>あなたが日本語文を修正してくれましたが，翻訳は改善されませんでした．</strong></div>")
(define *instruction-message* "<div class=\"notification_of_exp\"><strong>Bridgerであるあなたの日本文の書き換えサービスによって、翻訳文の品質が改善されます。一定の知識が伝わった場合、知識伝達がうまくいったことのお知らせや、作業に対する感謝のメッセージが、届くことがあります。</strong>")
(define *caution-message-to-faci* (string-append "<div class=\"notification_of_exp\"><strong>被験者IDと実験設定IDを確認して下さい．<br>" "subject-id=" *exp-subject-id* ", settings-id=" *exp-settings-id* "</strong>"))

