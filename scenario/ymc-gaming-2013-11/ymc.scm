(util:load-resource "scenario/ymc-gaming-2013-11/settings.scm")

(define-alias Bridger org.magcruise.gaming.scenario.ymc.incentive.JPBridger)
(define-alias Evaluator org.magcruise.gaming.scenario.ymc.incentive.Evaluator)
(define-alias YMCContext org.magcruise.gaming.scenario.ymc.incentive.YMCContext)

(define-namespace bridger "bridger")
(define-namespace evaluator "evaluator")

(define (def:setup-game-builder game-builder ::GameBuilder)
  (def:context YMCContext)
  (def:player 'Bridger 'human Bridger)
  (def:player 'Evaluator 'human Evaluator)

  (def:round
  (def:stage 'init
    (def:task 'Bridger 'bridger:init))
  (def:stage 'bridge-eval
    (def:task 'Bridger 'bridger:edit)
    (def:task 'Evaluator 'evaluator:evaluate)))

  (def:rounds 2
    (def:stage 'receive-eval
      (def:task 'Bridger 'bridger:receive-eval))
    (def:restage 'bridge-eval)))

(define (bridger:init ctx ::YMCContext self ::Bridger)
  (self:showMessage *instruction-message*))

(define (bridger:receive-eval ctx ::YMCContext self ::Bridger)
  (define evaluator ::Evaluator (ctx:getPlayer 'Evaluator)))

(define (bridger:edit ctx ::YMCContext self ::Bridger)
  (define a-orig-text (*a-orig-texts*:get ctx:roundnum))
  (define q-orig-text (*q-orig-texts*:get ctx:roundnum))
  (define prev-revised-text a-orig-text)

  (self:showMessage
    (<p> "原文" ctx:roundnum "の修正をはじめて下さい．"))
  (bridger:edit-aux ctx self q-orig-text a-orig-text a-orig-text))
  
(define (bridger:edit-aux ctx ::YMCContext self ::Bridger q-orig-text a-orig-text prev-revised-text)
  (self:syncRequestToInput 
    (make Form
      (<div>
        (<p> "質問文" ctx:roundnum ) (<blockquote> q-orig-text)
        (<p> "原文" ctx:roundnum) (<blockquote> a-orig-text)
        (<p>(<strong> "上の文章を翻訳しやすい日本語文に修正して下さい．")))
      (make TextInput (<strong> "修正した日本語文")  'revised-sentence prev-revised-text))
    (lambda (revised-sentence)
      (set! self:revisedSentence revised-sentence)))

  (define translated-sentence (translation "ja" "en" self:revisedSentence))
  (define back-translated-sentence (translation "en" "ja" translated-sentence))

  (self:syncRequestToInput 
    (make Form (make-msg ctx:roundnum q-orig-text a-orig-text self:revisedSentence translated-sentence back-translated-sentence)
      (make RadioInput 
          (<strong> "これで日本語の修正を終えますか？") 'again-or-finish "AGAIN" (list "再修正" "修正完了") (list "AGAIN" "FINISH")))
    (lambda (again-or-finish)
      (set! self:againOrFinish again-or-finish)))
  (self:showMessage (make-msg ctx:roundnum q-orig-text a-orig-text self:revisedSentence translated-sentence back-translated-sentence))

  (if (equal? self:againOrFinish "FINISH")
    (set! ctx:sentence translated-sentence)
    (bridger:edit-aux ctx self q-orig-text a-orig-text self:revisedSentence)))

(define (make-msg roundnum q-orig-text a-orig-text revisedSentence translated-sentence back-translated-sentence)
  (<div>
    (<p> "Question" roundnum)(<blockquote> q-orig-text)
    (<p> "Original Sentence" roundnum)(<blockquote> a-orig-text)
    (<p> "Revised Sentence")(<blockquote> revisedSentence)
    (<p> "Translation")(<blockquote> translated-sentence)
    (<p> "Backtranslation")(<blockquote> back-translated-sentence)))

(define (evaluator:evaluate ctx ::YMCContext self ::Evaluator)
  (self:syncRequestToInput 
    (make Form (<p> ctx:sentence)
      (make RadioInput 
          (<strong> "Aさん，この文章は分かりやすいですか？") '_scoreA "0" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5"))
      (make RadioInput 
          (<strong> "Bさん，この文章は分かりやすいですか？") '_scoreB "0" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5"))
      (make RadioInput 
          (<strong> "Cさん，この文章は分かりやすいですか？") '_scoreC "0" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5")))
    (lambda (_scoreA _scoreB _scoreC)
      (log:debug _scoreA _scoreB _scoreC))))

(define (translation src_lang target_lang src_text)
  (define client (langrid:make-client TranslationWithTemporalDictionaryService
    "TranslationCombinedWithBilingualDictionaryWithLongestMatchSearch"
    '("TranslationPL" "KyotoUJServer")
    '("BilingualDictionaryWithLongestMatchSearchPL"
        "http://robin.ai.soc.i.kyoto-u.ac.jp/ymccommunitydictionary/services/YMCCommunityDictionary")))
    (client:translate src_lang target_lang src_text (ArrayUtil:array Translation) target_lang))
