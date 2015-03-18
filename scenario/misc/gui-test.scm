(define (def:game-scenario)
  (def:player 'Tester 'human)
  (def:round 
    (def:stage 'gui-test
      (def:task 'Tester 'gui-test))))

(define (gui-test context ::Context self ::Player)
  (ui:show-message 'all
    (to-string "<h2>メッセージにはHTMLタグも使えるので，画像の挿入も出来ます．</h2>"
               "<img src=\"http://www.magcruise.org/jp/wp-content/themes/magcruise/img/logo.png\">"))

  (ui:request-to-input self:name
    (make Form "あなたの手を選んでください．"
      (make RadioInput  "手" 'hand "gu" (list "グー" "チョキ" "パー") (list "gu" "choki" "pa")))
    (lambda (hand)
      (ui:show-message 'all "あなたが入力した手は " hand " です．")))
  (ui:show-message  'all "じゃんけんの入力結果を受けとるまで，このメッセージが表示されないことに注意して下さい．")

  (ui:request-to-input self:name
    (make Form "あなたの属性とタイカレーの好みについて教えて下さい．"
      (make TextInput "名前" 'name "Scheme 太郎")
      (make ValInput "年齢" 'age 20)
      (make RadioInput "好きなタイカレーは?" 'thai_curry "green" (list "赤" "黄" "緑") (list "red" "yellow" "green")))
    (lambda (name age thai_curry)
      (ui:show-message  'all 
        (to-string "name=" name ", age=" age ", thai_curry=" thai_curry))))
  (ui:show-message 'all "カレーの入力結果を受けとるまで，このメッセージが表示されないことに注意して下さい．")

)


