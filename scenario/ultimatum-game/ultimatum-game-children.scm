(define (htmlext:rt content)
  (htmlext:tag "rt" content))

(define (htmlext:rb content)
  (htmlext:tag "rb" content))

(define (htmlext:rp content)
  (htmlext:tag "rp" content))

(define (htmlext:ruby rbc rtc)
  (htmlext:tag  "ruby" (htmlext:rb  rbc) (htmlext:rp  "(") (htmlext:rt rtc) (htmlext:rp  ")")))

(define-alias <rb> htmlext:rb)
(define-alias <rt> htmlext:rt)

(define-alias <ruby> htmlext:ruby)


;; 最後通牒ゲーム

(define-namespace ult "ult")
(define (ult:assign ctx ::Context u1 u2)
  (def:assign ctx u1 'BigBear)
  (def:assign ctx u2 'SmallBear))

(define provided-val 100000)

(define-alias UltPlayer org.magcruise.gaming.scenario.ultimatum.UltPlayer)


(define (def:game-scenario)
  (def:ext-player 'BigBear 'human UltPlayer)
  (def:ext-player 'SmallBear 'human UltPlayer)

  (def:round
    (def:stage 'start-of-round
      (def:task 'notify-round))
    (def:stage 'init
      (def:task 'BigBear 'init)
      (def:task 'SmallBear 'init))
    (def:stage 'negotiation
      (def:task 'BigBear 'first-player)
      (def:task 'SmallBear 'second-player)
      (def:task 'paid-model)))

  (def:rounds 9
    (def:restage 'start-of-round)
    (def:stage 'status
      (def:task 'BigBear 'status)
      (def:task 'SmallBear 'status))
    (def:restage 'negotiation))

  (def:round
    (def:restage 'status)))

(define (notify-round ctx ::Context)
  (manager:show-message 'all (to-string (<ruby> "第" "だい") ctx:roundnum "ラウンドです．")))

(define (init cxt ::Context self ::Player)
  (set! self:account 0))

(define (status cxt ::Context self ::Player)
  (manager:show-message self:name (self:tabulateHistory 'proposition 'yesOrNo 'acquisition 'account)))

(define proposition 0)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 通牒者プレーヤ(BigBear)のモデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (first-player context ::Context self ::Player)
  (manager:sync-request-to-input self:name
    (ui:form 
      (to-string
        "おおぐま君，<br>あなたは" provided-val "円を" (<ruby> "受" "う") "けとりました．こぐま君にいくらを" (<ruby> "分" "わ") "けますか？"
        (<div-class> "pull-right" (<img> "http://res.nkjmlab.org/www/img/WASEDA_BEAR_BIG.png")))
      (ui:number (<ruby> "金額" "きんがく") 'proposition 10000 (Min 0) (Max 100000)))
    (lambda (prop ::number)
      (set! self:proposition prop)
      (set! proposition prop)
      (define msg ::Message (self:makeMessage 'proposition (cons 'proposition prop)))
      (manager:send-message 'SmallBear msg))))

(define yes-or-no "")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 判断者プレーヤ(SmallBear)のモデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (second-player context ::Context self ::Player)
  (define rec-msg (self:msgbox:pop))
  (log:debug rec-msg)
  (manager:sync-request-to-input self:name
    (ui:form (to-string "こぐま君，<br>おおぐま君は" provided-val "円を" (<ruby> "受" "う") "け取り，あなたに"
                (rec-msg:get 'proposition) "円を" (<ruby> "分" "わ") "けると言いました．" (<ruby> "受" "う") "けとりますか？"
                (<div-class> "pull-right" (<img> "http://res.nkjmlab.org/www/img/WASEDA_BEAR_SMALL.png")))
      (ui:radio (to-string (<ruby> "受" "う") "けとる？") 'yes-or-no "yes" (list "yes" "no") (list "yes" "no")))
    (lambda (y-n)
      (set! self:proposition proposition)
      (set! self:yesOrNo y-n)
      (set! yes-or-no y-n))))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 金銭の授受の計算モデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (paid-model context ::Context)
  (define BigBear ::Player (context:getPlayer 'BigBear))
  (define SmallBear ::Player (context:getPlayer 'SmallBear))

  (if (equal? yes-or-no "yes")
    (money-paid)
    (money-not-paid))

  (define (money-paid)
    (let* ((val-of-p2 proposition)
           (val-of-p1 (- 100000 proposition)))
      (set! BigBear:account (+ BigBear:account val-of-p1))
      (set! BigBear:acquisition val-of-p1)
      (set! BigBear:yesOrNo "yes")
      (set! SmallBear:account (+ SmallBear:account val-of-p2))
      (set! SmallBear:acquisition val-of-p2)
      (set! SmallBear:yesOrNo "yes")
      (manager:show-message 'all
        (<div-class> "alert alert-success" 
          (to-string (<ruby> "交渉" "こうしょう") "が" (<ruby> "成立" "せいりつ")  "しました．")
          (html:ul
            (to-string "おおぐま君は" val-of-p1 "円を手に入れました．")
            (to-string "こぐま君は" val-of-p2 "円を手に入れました．"))))))

  (define (money-not-paid)
    (manager:show-message 'all (<div-class> "alert alert-warning" (<ruby> "交渉" "こうしょう") "は" (<ruby> "成立" "せいりつ") "しませんでした"))
      (set! BigBear:acquisition 0)
      (set! BigBear:yesOrNo "no")
      (set! SmallBear:acquisition 0)
      (set! SmallBear:yesOrNo "no")      
    )
)
