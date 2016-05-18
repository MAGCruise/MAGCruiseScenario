(org.magcruise.gaming.langrid.LangridSchemePlugin:load)

(define-alias YMCContext org.magcruise.gaming.tutorial.ymc.incentive.actor.YMCContext)
(define-alias JPExpert org.magcruise.gaming.tutorial.ymc.incentive.actor.JPExpert)
(define-alias JPBridger org.magcruise.gaming.tutorial.ymc.incentive.actor.JPBridger)
(define-alias VTBridger org.magcruise.gaming.tutorial.ymc.incentive.actor.VTBridger)
(define-alias VTYouth org.magcruise.gaming.tutorial.ymc.incentive.actor.VTYouth)

(define-namespace jp-expert "jp-expert")
(define-namespace jp-bridger "jp-bridger")
(define-namespace vt-bridger "vt-bridger")
(define-namespace vt-youth "vt-youth")

(define *JP-Bridger* ::JPBridger #!null)
(define *VT-Youth*    ::VTYouth #!null)
(define *JP-Expert* ::JPExpert #!null)

(define (def:setup-game-builder builder ::GameBuilder)
  (builder:setDefContext
    (def:context YMCContext))

  (builder:addDefPlayers
    (def:player 'JP-Expert 'agent JPExpert)
    (def:player 'JP-Bridger 'human JPBridger)
    (def:player 'VT-Bridger 'agent VTBridger)
    (def:player 'VT-Youth 'human VTYouth))

  (builder:addDefRounds
    (def:rounds 2
        (def:stage 'init
            (def:task 'initialize)
            (def:task 'VT-Youth 'vt-youth:init)
            (def:task 'JP-Expert 'jp-expert:init)
            (def:task 'JP-Bridger 'jp-bridger:init))
        (def:stage 'question-answer
            (def:task 'VT-Youth 'vt-youth:question)
            (def:task 'JP-Expert 'jp-expert:answer)
            (def:task 'JP-Bridger 'jp-bridger:bridge-jp-en)
            (def:task 'VT-Bridger 'vt-bridger:bridge-en-vt))
        (def:stage 'feedback
            (def:task 'VT-Youth 'vt-youth:decide-feedback)))))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (jp-bridger:init ctx ::YMCContext self ::JPBridger)
    (when (eqv? ctx:roundnum 0)
        (self:showMessage *instruction-message*)))

(define (vt-youth:init ctx ::YMCContext self ::VTYouth)
    (self:init ctx:roundnum))

(define (jp-expert:init ctx ::YMCContext self ::JPExpert)
    (self:init ctx:roundnum))

(define (jp-bridger:bridge-jp-en ctx ::YMCContext self ::JPBridger)
    (self:showMessage
        (to-string
        (<div-class> "panel panel-warning"
            (<div-class> "panel-heading"
                (<h4-attr> '(("class ""panel-title")) "No. " ctx:roundnum " : Question (for demo)"))
            (<div-class> "panel-body" *VT-Youth*:questionEn))
        (<div-class> "panel panel-warning"
            (<div-class> "panel-heading"
                (<h4-attr> '(("class ""panel-title")) "No. " ctx:roundnum " : Answer (ORIGINAL sentences translated by MT) (for demo)"))
            (<div-class> "panel-body"  (translation "ja" "en" *JP-Expert*:answer)))))


    (define (rewrite self ::JPBridger ctx ::YMCContext prev-revised-text)
        (self:syncRequestToInput
            (ui:form
                (<div>
                    (<div-class> "panel panel-success"
                        (<div-class> "panel-heading"
                            (<h4-attr> '(("class ""panel-title")) "No. " ctx:roundnum " : Question"))
                        (<div-class> "panel-body" *VT-Youth*:question))
                    (<div-class> "panel panel-success"
                        (<div-class> "panel-heading"
                            (<h4-attr> '(("class ""panel-title")) "No. " ctx:roundnum " : Answer (original)"))
                        (<div-class> "panel-body"    *JP-Expert*:answer))
                    (<div-class> "alert alert-info" "Please, rewrite the sentences to raise machine translation quality. "))
                (ui:textarea "Revised sentences"  'revised-sentence prev-revised-text))
            (lambda (revised-sentence)
                (set! self:revisedSentence revised-sentence)))

        (self:showMessage
          (to-string
            (<div-class> "panel panel-warning"
                (<div-class> "panel-heading"
                    (<h4-attr> '(("class ""panel-title")) "No. " ctx:roundnum " : Answer (REVISED sentences translated by MT) (for demo)"))
                (<div-class> "panel-body" (translation "ja" "en" self:revisedSentence)))))

         (self:syncRequestToInput
            (ui:form
                (<div>
                     (<p> "No. " ctx:roundnum " : Question")(<pre> *VT-Youth*:question)
                     (<p> "No. " ctx:roundnum " : Answer")(<pre> *JP-Expert*:answer)
                     (<p> "Revised sentences")(<pre> self:revisedSentence)
                     (<p> "Result of back-translation (jp->en->jp)")
                        (<pre> (translation "en" "ja" (translation "ja" "en" self:revisedSentence))))
                 (ui:radio
                        "Do you want to finish rewriting?"
                        'again-or-finish "Finish" (list "Finish" "Again") (list "Finish" "Again")))
             (lambda (again-or-finish)
                (unless
                    (equal? again-or-finish "Finish")
                    (rewrite self ctx self:revisedSentence)))))

    (rewrite self ctx  *JP-Expert*:answer))

(define (vt-youth:decide-feedback ctx ::YMCContext self ::VTYouth)
    (self:syncRequestToInput
        (ui:form
            (<div>
                (<div-class> "panel panel-warning"
                    (<div-class> "panel-heading"
                        (<h4-attr> '(("class ""panel-title")) "No. " ctx:roundnum " : Question"))
                    (<div-class> "panel-body" *VT-Youth*:questionEn))
                (<div-class> "panel panel-warning"
                    (<div-class> "panel-heading"
                        (<h4-attr> '(("class ""panel-title")) "No. " ctx:roundnum " : Answer (ORIGINAL sentences translated by MT)"))
                    (<div-class> "panel-body"  (translation "ja" "en" *JP-Expert*:answer)))
                (<div-class> "panel panel-warning"
                    (<div-class> "panel-heading"
                        (<h4-attr> '(("class ""panel-title")) "No. " ctx:roundnum " : Answer (REVISED sentences translated by MT)"))
                    (<div-class> "panel-body"  (translation "ja" "en" *JP-Bridger*:revisedSentence))))
         (ui:radio
                "Do you want to send a thanks message to Bridger?"
                'yes-or-no "Yes" (list "Yes" "No") (list "Yes" "No")))
        (lambda (yes-or-no)
            (when
                (equal? yes-or-no "Yes")
                (*JP-Bridger*:showMessage *thanks-message*)))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (translation src_lang target_lang src_text)
    (define client ::jp.go.nict.langrid.service_1_2.translation.TranslationWithTemporalDictionaryService
         (langrid:make-client jp.go.nict.langrid.service_1_2.translation.TranslationWithTemporalDictionaryService
            "TranslationCombinedWithBilingualDictionaryWithLongestMatchSearch"
             '(("TranslationPL" "KyotoUJServer")
                ("BilingualDictionaryWithLongestMatchSearchPL"
                "http://robin.ai.soc.i.kyoto-u.ac.jp/ymccommunitydictionary/services/YMCCommunityDictionary"))))
        (client:translate src_lang target_lang src_text (jp.go.nict.langrid.service_1_2.bilingualdictionary.Translation[]) target_lang))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (initialize ctx ::YMCContext)
    (set! *JP-Bridger*    (ctx:getPlayer 'JP-Bridger))
    (set! *VT-Youth*     (ctx:getPlayer 'VT-Youth))
    (set! *JP-Expert* (ctx:getPlayer 'JP-Expert))
    (ctx:initialize))

(define *thanks-message*
    (<div-class> "alert alert-info"
        "Thank you for your rewriting! I got the answer."))

(define *instruction-message*
    (<div-class> "alert alert-info"
        "Please, rewrite assigned Japanese sentences to raise machine translation quality. "
        "The sentecnes are the answers from Japanese Rice Expert to VT Youth. "
        "Sometimes, you will get feedback from VT Youth."))

(define (vt-youth:question ctx ::YMCContext self ::VTYouth)    #t)
(define (jp-expert:answer ctx ::YMCContext self ::JPExpert)    #t)
(define (vt-bridger:bridge-en-vt ctx ::YMCContext self ::VTBridger)    #t)




