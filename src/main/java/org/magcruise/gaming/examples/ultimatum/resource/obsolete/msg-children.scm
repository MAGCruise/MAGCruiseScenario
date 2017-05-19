(define-alias UltPlayer org.magcruise.gaming.examples.ultimatum.actor.UltimatumPlayer)
(define-alias FirstPlayer org.magcruise.gaming.examples.ultimatum.actor.FirstPlayer)
(define-alias SecondPlayer org.magcruise.gaming.examples.ultimatum.actor.SecondPlayer)
(define-alias FinalNote org.magcruise.gaming.examples.ultimatum.msg.FinalNote)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 通牒者プレーヤ(FirstPlayer)のモデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (note-form ctx ::UltimatumGameContext self ::FirstPlayer) ::Form
  (ui:form
      (to-string
        (<ruby> "第" "だい") ctx:roundnum "ラウンドです．"
        "おおぐま君，<br>あなたは" UltimatumGameContext:providedVal "円を" (<ruby> "受" "う") "けとりました．こぐま君にいくらを" (<ruby> "分" "わ") "けますか？"
        (<div> class: "pull-right" (<img> src: "https://i.gyazo.com/eddcf71e96234685cbaa412c463b9c7a.jpg")))
      (ui:number (<ruby> "金額" "きんがく") 'proposition (self:defaultPropositions ctx:roundnum)
                 (Min 0) (Max UltimatumGameContext:providedVal) (Required))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 判断者プレーヤ(SecondPlayer)のモデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (judge-form ctx ::UltimatumGameContext self ::SecondPlayer rec-msg ::FinalNote) ::Form
    (ui:form (to-string
                (<ruby> "第" "だい") ctx:roundnum "ラウンドです．"
                "こぐま君，<br>おおぐま君は" UltimatumGameContext:providedVal "円を" (<ruby> "受" "う") "け取り，あなたに"
                rec-msg:proposition "円を" (<ruby> "分" "わ") "けると言いました．" (<ruby> "受" "う") "けとりますか？"
                (<div> class: "pull-right" (<img> src: "https://i.gyazo.com/d4cf1336d68f315fc9a88ba446f69488.jpg")))
             (ui:radio (to-string (<ruby> "受" "う") "けとる？") 'yes-or-no (self:getDefaultYesOrNo ctx:roundnum) (list "yes" "no") (list "yes" "no") (Required))))


(define (makeResultMessage val1 val2) ::String
   (<div> class: "alert alert-success"
    (to-string (<ruby> "交渉" "こうしょう") "が" (<ruby> "終" "お")  "わりました．")
          (html:ul
            (to-string "おおぐま君は" val1 "円を手に入れました．")
            (to-string "こぐま君は" val2 "円を手に入れました．"))))

