
(define-alias YMCContext org.magcruise.gaming.scenario.ymc.incentive.YMCContext)
(define-alias JPExpert org.magcruise.gaming.scenario.ymc.incentive.JPExpert)
(define-alias JPBridger org.magcruise.gaming.scenario.ymc.incentive.JPBridger)
(define-alias VTBridger org.magcruise.gaming.scenario.ymc.incentive.VTBridger)
(define-alias VTYouth org.magcruise.gaming.scenario.ymc.incentive.VTYouth)

(define-namespace jp-expert "jp-expert")
(define-namespace jp-bridger "jp-bridger")
(define-namespace vt-bridger "vt-bridger")
(define-namespace vt-youth "vt-youth")

(define (def:game-scenario)
  (def:ext-context YMCContext)
  (def:ext-player 'JP-Expert 'agent JPExpert)
  (def:ext-player 'JP-Bridger 'human JPBridger)
  (def:ext-player 'VT-Bridger 'agent VTBridger)
  (def:ext-player 'VT-Youth 'agent VTYouth)

  (def:rounds 1
    (def:stage 'init
      (def:task 'initialize))
    (def:stage 'question-answer
      (def:task 'VT-Youth 'vt-youth:question)
      (def:task 'JP-Expert 'jp-expert:answer)
      (def:task 'JP-Bridger 'jp-bridger:bridge-jp-en)
      (def:task 'VT-Bridger 'vt-bridger:bridge-en-vt))
    (def:stage 'feedback
      (def:task 'VT-Youth 'vt-youth:decide-feedback))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define *JP-Bridger* ::JPBridger #!null)
(define *VT-Youth*  ::VTYouth #!null)
(define *JP-Expert* ::JPExpert #!null)



(define (initialize ctx ::YMCContext)
  (set! *JP-Bridger*  (ctx:getPlayer 'JP-Bridger))
  (set! *VT-Youth*   (ctx:getPlayer 'VT-Youth))
  (set! *JP-Expert* (ctx:getPlayer 'JP-Expert)))


(define (jp-bridger:init ctx ::YMCContext self ::JPBridger)
  (ui:show-message 'JP-Bridger *instruction-message*))
  
  
(define (jp-bridger:bridge-jp-en ctx ::YMCContext self ::JPBridger)    
    (ui:show-message self:name
        (to-string "Please, rewrite the answer of No. " (<strong> ctx:roundnum)))

    (define (rewrite self ::JPBridger ctx ::YMCContext question-orig-text answer-orig-text prev-revised-text)
      (ui:request-to-input self:name
        (make Form (to-string 
                      "Question No. "
                      (number->string ctx:roundnum)
                      (<br>) (<pre> question-orig-text)
                      (<br>) "Original sentences of the answer"
                      (number->string ctx:roundnum)
                      (<br>) (<pre> answer-orig-text)
                      (<p> (<strong> "Please, rewrite the sentences to raise translation quality. ")))
              (make TextInput (<strong> "Revised sentences")  'revised-sentence prev-revised-text))
        (lambda (revised-sentence)
          (set! self:revisedSentence revised-sentence)))
      
      (let* ((translated-sentence (translation "ja" "en" self:revisedSentence))
             (back-translated-sentence (translation "en" "ja" translated-sentence)))
          (ui:request-to-input self:name
              (make Form 
                (to-string 
                    "質問文" (number->string ctx:roundnum) 
                    "は以下です．<br>" (<pre> question-orig-text)
                    "原文" (number->string ctx:roundnum) 
                    "は以下です．<br>" (<pre> answer-orig-text)
                    "修正した文章は以下です．<br>" (<pre> self:revisedSentence)
                    "翻訳(日→英)の結果は以下です．<br>" (<pre> translated-sentence)
                    "折り返し翻訳(日→英→日)の結果は以下です．<br>" (<pre> back-translated-sentence))
                (make RadioInput 
                        (<strong> "これで日本語の修正を終えますか？")
                        'again-or-finish "AGAIN" (list "再修正" "修正完了") (list "AGAIN" "FINISH")))
            (lambda (again-or-finish)
              (self:set 'again-or-finish again-or-finish)))

        (ui:show-message 'JapaneseBridger
            (to-string (<br>) (<strong> "・質問文" ctx:roundnum) (<br>) question-orig-text 
                       (<br>) (<strong> "・原文" ctx:roundnum) (<br>) answer-orig-text 
                       (<br>) (<strong> "・修正した文") (<br>) self:revisedSentence
                       (<br>) (<strong> "・翻訳(日→英)の結果") (<br>) translated-sentence
                       (<br>) (<strong> "・折り返し翻訳(日→英→日)の結果") (<br>) back-translated-sentence)))

          (unless (equal? (self:get 'again-or-finish) "FINISH")
              (japanese-bridger-behavior self ctx 
                  question-orig-text answer-orig-text self:revisedSentence)))
    (rewrite self ctx *VT-Youth*:question *JP-Expert*:answer *JP-Expert*:answer))



(define (translation src_lang target_lang src_text)
  (define client ::TranslationWithTemporalDictionaryService
     (langrid:make-client TranslationWithTemporalDictionaryService
      "TranslationCombinedWithBilingualDictionaryWithLongestMatchSearch"
      (list
        '("TranslationPL" "KyotoUJServer")
        '("BilingualDictionaryWithLongestMatchSearchPL"
          "http://robin.ai.soc.i.kyoto-u.ac.jp/ymccommunitydictionary/services/YMCCommunityDictionary"))))
    (client:translate src_lang target_lang src_text (make Translation[]) target_lang))

(define *thanks-message* (<div-attr> '(("class" "notification_of_exp")) (<strong> "あなたの修正により，ベトナムの児童に正しく情報が伝わりました．ありがとうございました．")))
(define *failure-message* (<div-attr> '(("class" "notification_of_exp")) (<strong> "あなたが日本語文を修正してくれましたが，翻訳は改善されませんでした．")))
(define *instruction-message* (<div-attr> '(("class" "notification_of_exp")) (<strong> 
             "Bridgerであるあなたの日本文の書き換えサービスによって、翻訳文の品質が改善されます。" 
             "一定の知識が伝わった場合、知識伝達がうまくいったことのお知らせや、作業に対する感謝のメッセージが、届くことがあります。")))


(define (vt-youth:question ctx ::YMCContext self ::VTYouth)  #t)
(define (jp-expert:answer ctx ::YMCContext self ::JPExpert)  #t)
(define (vt-bridger:bridge-en-vt ctx ::YMCContext self ::VTBridger)  #t)
(define (vt-youth:decide-feedback ctx ::YMCContext self ::VTYouth)  #t)
