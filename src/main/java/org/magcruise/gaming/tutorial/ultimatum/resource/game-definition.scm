;; 最後通牒ゲーム
(define-namespace ult "ult")

(define-alias UltPlayer org.magcruise.gaming.tutorial.ultimatum.actor.UltimatumPlayer)
(define-alias FirstPlayer org.magcruise.gaming.tutorial.ultimatum.actor.FirstPlayer)
(define-alias SecondPlayer org.magcruise.gaming.tutorial.ultimatum.actor.SecondPlayer)
(define-alias UltimatumGameContext org.magcruise.gaming.tutorial.ultimatum.actor.UltimatumGameContext)
(define-alias FinalNote org.magcruise.gaming.tutorial.ultimatum.msg.FinalNote)


(define (def:setup-game-builder game-builder ::GameBuilder)


  (game-builder:addDefContext (def:context UltimatumGameContext))

  (setup-players game-builder)

  (game-builder:addDefRounds
    (def:round
      (def:stage 'negotiation
        (def:task 'BigBear 'note)
        (def:task 'SmallBear 'judge)
        (def:task 'paid)))

    (def:rounds 9
      (def:stage 'status
        (def:task 'BigBear 'status)
        (def:task 'SmallBear 'status))
      (def:restage 'negotiation))

    (def:round
      (def:restage 'status))))

(define (ult:assign builder ::GameBuilder  u1 ::symbol u2 ::symbol)
  (builder:addDefAssignRequests
    (def:assignment-request 'BigBear (symbol->string u1))
    (def:assignment-request 'SmallBear (symbol->string u2))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 通牒者プレーヤ(BigBear)のモデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (note-form ctx ::UltimatumGameContext self ::FirstPlayer) ::Form
  (ui:form
      (to-string
        (<ruby> "第" "だい") ctx:roundnum "ラウンドです．"
        "おおぐま君，<br>あなたは" UltimatumGameContext:providedVal "円を" (<ruby> "受" "う") "けとりました．こぐま君にいくらを" (<ruby> "分" "わ") "けますか？"
        (<div-class> "pull-right" (<img> "http://res.nkjmlab.org/www/img/WASEDA_BEAR_BIG.png")))
      (ui:number (<ruby> "金額" "きんがく") 'proposition (self:defaultPropositions ctx:roundnum) (Min 0) (Max UltimatumGameContext:providedVal))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 判断者プレーヤ(SmallBear)のモデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (judge-form ctx ::UltimatumGameContext self ::SecondPlayer rec-msg ::FinalNote) ::Form
    (ui:form (to-string
                (<ruby> "第" "だい") ctx:roundnum "ラウンドです．"
                "こぐま君，<br>おおぐま君は" UltimatumGameContext:providedVal "円を" (<ruby> "受" "う") "け取り，あなたに"
                rec-msg:proposition "円を" (<ruby> "分" "わ") "けると言いました．" (<ruby> "受" "う") "けとりますか？"
                (<div-class> "pull-right" (<img> "http://res.nkjmlab.org/www/img/WASEDA_BEAR_SMALL.png")))
      (ui:radio (to-string (<ruby> "受" "う") "けとる？") 'yes-or-no (self:getDefaultYesOrNo ctx:roundnum) (list "yes" "no") (list "yes" "no"))))


(define (makeResultMessage val1 val2) ::String
   (<div-class> "alert alert-success"
          (to-string (<ruby> "交渉" "こうしょう") "が" (<ruby> "成立" "せいりつ")  "しました．")
          (html:ul
            (to-string "おおぐま君は" val1 "円を手に入れました．")
            (to-string "こぐま君は" val2 "円を手に入れました．"))))

