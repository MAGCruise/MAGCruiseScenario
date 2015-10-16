
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
      (def:task 'VT-Youth 'vt-youth:decide-feedback)))

  (def:rounds 1
    (def:restage 'question-answer)
    (def:restage 'feedback)))


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
        (ui:form (to-string 
                      (<p> "Question No. " ctx:roundnum) (<pre> question-orig-text)
                      (<p> "Original sentences of Answer No. " ctx:roundnum)(<pre> answer-orig-text)
                      (<p> (<strong> "Please, rewrite the sentences to raise translation quality. ")))
              (ui:textarea (<strong> "Revised sentences")  'revised-sentence prev-revised-text))
        (lambda (revised-sentence)
          (set! self:revisedSentence revised-sentence)))
      
      (let* ((translated-sentence (translation "ja" "en" self:revisedSentence))
             (back-translated-sentence (translation "en" "ja" translated-sentence)))
          (ui:request-to-input self:name
              (ui:form 
                (to-string 
                    (<p> "Question No. " ctx:roundnum )(<pre> question-orig-text)
                    (<p> "Original senteces" ctx:roundnum )(<pre> answer-orig-text)
                    (<p> "Revised sentences")(<pre> self:revisedSentence)
                    (<p> "Result of translation (jp->en)")(<pre> translated-sentence)
                    (<p> "Result of back-translation (jp->en->jp)")(<pre> back-translated-sentence))
                (ui:radio 
                        (<strong> "Do you want to finish rewriting?")
                        'again-or-finish "AGAIN" (list "Again" "Finish") (list "AGAIN" "FINISH")))
            (lambda (again-or-finish)
              (self:set 'again-or-finish again-or-finish)))

        (ui:show-message 'JapaneseBridger
            (to-string (<br>) (<strong> " - Question No. " ctx:roundnum) (<br>) question-orig-text 
                       (<br>) (<strong> " - Original senteces" ctx:roundnum) (<br>) answer-orig-text 
                       (<br>) (<strong> " - Revised sentences") (<br>) self:revisedSentence
                       (<br>) (<strong> " - Result of translation (jp->en)") (<br>) translated-sentence
                       (<br>) (<strong> " - Result of back-translation (jp->en->jp)") (<br>) back-translated-sentence)))

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
