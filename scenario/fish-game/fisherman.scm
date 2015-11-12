(define-namespace fisher "fisherman")
(define-simple-class Ocean (<java.io.Serializable>)
  (fishes ::integer init-keyword: fishes:)
  (recovery-rate ::float init-keyword: recovery-rate:)
  ((*init* (fishes ::integer) (recovery-rate ::float))
     (set! (this):fishes fishes)
     (set! (this):recovery-rate recovery-rate))
  ((capture count)
    (set! (this):fishes (- (this):fishes count)))
  ((recover)
    (set! (this):fishes (integer (* (this):fishes (this):recovery-rate)))))

(define ocean ::Ocean (make Ocean 100 1.2))

(define decisions '())

(define (go-to-fishing ctx ::Context)
  (define (calc-ratio a b)
    (if (< a b) (exact->inexact (/ a b)) 1))

  (define sum-of-dicision (sum decisions))
  (define calculated-ratio (calc-ratio ocean:fishes sum-of-dicision))

  (if (> ocean:fishes 0)
    (for-each
      (lambda (player ::Player)
        (define count (player:get 'decision))
        (define capture-count (integer (floor (* count calculated-ratio))))
        (ocean:capture capture-count)
        (player:set 'capture-count capture-count)
        (manager:show-message 'all player:name "が，魚を" capture-count "匹とりました．"))
     ctx:players:all)))

(define (cleanup-and-recover ctx ::Context)
  (set! decisions '())
  (ocean:recover)
  (manager:show-message 'all "ラウンド" ctx:roundnum "が終了しました．"))

(define (decisions-for-each ctx itelator)
  (for-each
    (lambda (pair) (callback (eval (car pair)) (cdr pair)))
    decisions))

(define (notify-status ctx ::Context )
  (manager:show-message 'all "現在，漁場には" ocean:fishes "匹の魚がいます．"))

(define (fisher:decide-number-of-fish ctx ::Context self ::Player)
  (self:syncRequestForInput 
    (ui:form "何匹の魚を取ろうとしますか？"
      (ui:number "目標漁獲量" 'number 10))
    (lambda (number ::number)
      (self:set 'decision number)
      (cons number decisions))))

(define (fisher:comment ctx ::Context self ::Player)
  (self:syncRequestForInput 
    (ui:form "他の漁師に伝えたいメッセージを入力して下さい．"
      (ui:text "何も無ければENDと入力して下さい．それ移行，このラウンド中は発言できなくなります．" 'text "END"))
    (lambda (text ::string)
      (self:set 'text text)
      (manager:show-message 'all self:name ": " (html:p text))))
  (unless (equal? (self:get 'text) "END") (fisher:comment ctx self)))
