(define *a-orig-texts* ::list
  (list 
  "もし私達が水田自然な敵に立ち寄るならば、自然な敵は水田から（キャッチ）昆虫をつかみます。" ;; 00
  "ネズミ. ネズミ破壊稲の近くの原因 米を食べる 。" ;;01
  "鹿は食事苗を満喫しました。 どうぞ、昆虫 バリアから鹿まで 水田のnot 。 高さ2mのフェンスを貸してください。" ;;02
  "かめ食事オタマジャクシ、土地ミミズ、および草。従ってnot 害稲 。" ;;03
  "さまざまな予防のための農薬使用は害虫の多様性に依存します。 まず第一に、それのためにそれが使われる種類 昆虫をチェックすることが重要です。" ;;04
  "米について稲の破壊水田と食事米を動物. イノシシによって踏みつけます。" ;;05
  "高い温度昼間〈低い温度夜の〉 それが田植えに適当な環境。" ;;06
  "光、温度、水、および肥料（肥料、改善者）は、米. 光の成長 条件に影響し、調整可能ではない（空気（ガス）ポストによって）温度けれども水、および肥料（肥料、改善者）はコントロールできます。" ;;07
  "収穫（作物）の後に、穀物の粒は太陽晴れの下に乾燥しています。 粒子はもし十分ではなく 晴れ ならば、等しくnotで乾燥します。" ;;08
  "ハリケーンは米に影響し、米を、白い色に切り替えさせられます。 結果。 それは種子トウモロコシを茶色にするか、または完全に円熟しているわけではありません。" ;;09
  "苗を1/3ヶ月に約1週植えた後に グラウンドで植えつけられた苗の根および成長を開始した 私達 霧を吹かせているべきである 1番目の除草剤（除草剤） 。" ;;10
))

(define *a-orig-scores* ::list
  (list "2.00" "2.30" "1.80" "2.20" "3.80" "2.70" "3.10" "3.20" "2.00" "2.50"))


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
  (ui:show-message self:name "Bridgerであるあなたの日本文の書き換えサービスによって，翻訳文の品質が改善されます．"))

(define (evaluator:init ctx ::YMCContext self ::Evaluator)
  (ui:show-message self:name "評価する文章が到着するまでお待ち下さい．"))

(define (bridger:receive-eval ctx ::YMCContext self ::Bridger)
  (define evaluator ::Evaluator (ctx:getPlayer 'Evaluator))
  (ui:show-message self:name (html:div class:"alert alert-info" (to-string "原文の評価値は" (*a-orig-scores*:get ctx:roundnum) "でした．" "修正した文章の評価値は" evaluator:averageScore "になりました．"))))

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
        (<p> "原文の評価値は" (<strong> (*a-orig-scores*:get ctx:roundnum)) "でした．")
       )
      (make TextInput "原文を分かりやすい日本語に修正して，入力して下さい．" 'revised-sentence prev-revised-text)
  )
    (lambda (revised-sentence)
      (set! self:revisedSentence revised-sentence)
　　　(ui:show-message self:name "文章の評価がされるまでお待ち下さい．")
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
          ( <strong>"これで修正を終えますか？") 'again-or-finish "AGAIN" (list "再修正" "修正完了") (list "AGAIN" "FINISH")))
    (lambda (again-or-finish)
      (when (equal? again-or-finish "AGAIN")
        (bridger:edit-aux ctx self a-orig-text self:revisedSentence)))))

(define (evaluator:evaluate ctx ::YMCContext self ::Evaluator)
  (define bridger ::Bridger (ctx:getPlayer 'Bridger))
  (ui:request-input self:name
    (make Form
　　　　 (<p> bridger:revisedSentence)
      (make RadioInput 
          "Aさん，この文章は分かりやすいですか？" '_scoreA "3" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5"))
      (make RadioInput 
          "Bさん，この文章は分かりやすいですか？" '_scoreB "3" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5"))
      (make RadioInput 
          "Cさん，この文章は分かりやすいですか？" '_scoreC "3" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5")))
    (lambda (_scoreA _scoreB _scoreC)
      (self:setAverageScore _scoreA _scoreB _scoreC)
      (ui:show-message self:name "評価する次の文章が到着するまでお待ち下さい．"))))