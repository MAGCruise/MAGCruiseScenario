(define *a-orig-texts* ::list
  (list 
"パッケージ化学薬品農業での喜ばしいgrandchildren.満了。 もし何年もの間、期限切れになった化学薬品を使うならば、それらは、尋ねるための効果. ありがとうを全然持っていません。" ;; 00
  "もし私達が水田のための有益な昆虫を使うならば、それは害昆虫をつかみます。" ;;01
  "いくつかの稲種が抵抗力があります ある一定の病気 （病気、病気）。 病気（病気、病気）のために耐える種稲の植え付けは、防止する測定です。" ;;02
  "しばしば米極少開花期（鎗）に有害な高い温度。 高い温度は強い蒸発のため乾燥状態も起こします。" ;;03
  "まだ正常な田植えを続けることができる時 量 米の小さい少し 除草剤（除草剤）の影響のため胴枯れ病である 。" ;;04
  "もし 指定された濃度の農薬の濃度 それが問題ではないならば。" ;;05
  "もし見つけるならば その時の米の異常な現象 最も近いプロフェッショナルに報告するべきである 。 現在、葉のテーブル色はメコンデルタで非常によく使われました。 ステータス米において依存し、私達は即座に追加のメソッドを選びます。" ;;06
  "種まき米多様性（品種、種）約1から4まで日の前に、耕すことと潅漑田をするべきです。 もしより早く雑草をするならば、もっと成長します。" ;;07
  "ほとんどのケースにおいて、私達は消毒のための化学薬品を使うけれども、湯行ない消毒も使うことができます。 そのケースにおいて 5分のための湯60℃の浸し 。" ;;08
  "多くの雑草がある場所でのイナゴしばしば出現。 するべきです 水田の雑草の除去 。" ;;09
  "稲のイナゴ食事葉。 イナゴ notで稲. イナゴの食事葉が水稲の穀粒も食べる時 イナゴの番号 より多く 。" ;;10
))


(define *a-orig-scores* ::list
  (list "1.50" "3.20" "2.60" "2.10" "1.80" "2.60" "1.30" "2.40" "3.40" "2.90"))



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
      (def:task 'Bridger 'bridger:init)
        (def:task 'Evaluator 'evaluator:init))
    (def:stage 'bridge-eval
      (def:task 'Bridger 'bridger:edit)))

  (def:rounds 2
    (def:stage 'receive-eval
      (def:task 'Bridger 'bridger:receive-eval))
    (def:restage 'bridge-eval)))

(define (bridger:init ctx ::YMCContext self ::Bridger)
  (self:showMessage (<div> "Bridgerであるあなたの日本文の書き換えサービスによって，翻訳文の品質が改善されます．")))

(define (evaluator:init ctx ::YMCContext self ::Evaluator)
  (self:showMessage "評価する文章が到着するまでお待ち下さい．"))

(define (bridger:receive-eval ctx ::YMCContext self ::Bridger)
  (define evaluator ::Evaluator (ctx:getPlayer 'Evaluator))
  (self:showMessage (html:div class:"alert alert-info" (to-string "原文の評価値は" (*a-orig-scores*:get ctx:roundnum) "でした．" "修正した文章の評価値は" evaluator:averageScore "になりました．"))))

(define (bridger:edit ctx ::YMCContext self ::Bridger)
  (define a-orig-text (*a-orig-texts*:get ctx:roundnum))
  (define prev-revised-text a-orig-text)

  (self:showMessage
    "原文" ctx:roundnum "の修正をはじめて下さい．")
  (bridger:edit-aux ctx self a-orig-text prev-revised-text))
  
(define (bridger:edit-aux ctx ::YMCContext self ::Bridger a-orig-text prev-revised-text)
  (self:syncRequestToInput 
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

  (self:syncRequestToInput 
    (make Form (make-msg)
      (make RadioInput 
          (<strong> "これで修正を終えますか？") 'again-or-finish "AGAIN" (list "再修正" "修正完了") (list "AGAIN" "FINISH")))
    (lambda (again-or-finish)
      (when (equal? again-or-finish "AGAIN")
        (bridger:edit-aux ctx self a-orig-text self:revisedSentence)))))

(define (evaluator:evaluate ctx ::YMCContext self ::Evaluator)
  (define bridger ::Bridger (ctx:getPlayer 'Bridger))
  (self:syncRequestToInput 
    (make Form (<p> bridger:revisedSentence)
      (make RadioInput 
          (<strong> "Aさん，この文章は分かりやすいですか？") '_scoreA "3" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5"))
      (make RadioInput 
          (<strong> "Bさん，この文章は分かりやすいですか？") '_scoreB "3" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5"))
      (make RadioInput 
          (<strong> "Cさん，この文章は分かりやすいですか？") '_scoreC "3" (String[] "1 (悪い) " "2" "3" "4" "5 (良い)") (String[] "1" "2" "3" "4" "5")))
    (lambda (_scoreA _scoreB _scoreC)
      (self:setAverageScore _scoreA _scoreB _scoreC)
      (self:showMessage "評価する次の文章が到着するまでお待ち下さい．"))))
