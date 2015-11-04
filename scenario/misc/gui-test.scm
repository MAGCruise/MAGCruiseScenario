(define (def:game-scenario)
  (def:player 'Tester 'human)
  (def:round 
    (def:stage 'gui-test
      (def:task 'Tester 'gui-test))))

(define (gui-test ctx ::Context self ::Player)
  (manager:show-message 'Tester
    (<div-class> "alert alert-info"
      (to-string (<h3> "プレーヤにメッセージを送れます．メッセージにはHTMLタグが使えます．")
                 (<p> "画像の挿入も出来ます．")
                 (<img> "http://www.magcruise.org/jp/wp-content/themes/magcruise/img/logo.png"))))

   (manager:sync-request-to-input self:name
     (ui:form "プレーヤには複数の項目を入力させることが出来ます．<br>あなたの食べ物の好みについて教えて下さい．"
       (ui:text "名前" 'name "MAGCruise 太郎")
       (ui:number "年齢" 'age 20)
       (ui:radio "好きなタイカレーは?" 'thai_curry "green" (list "赤" "黄" "緑") (list "red" "yellow" "green"))
       (ui:checkbox "好きな果物は?" 'drinks (list "lemmon" "melon") (list "リンゴ" "レモン" "メロン") (list "apple" "lemmon" "melon")))
     (lambda (name ::string age ::number thai_curry ::string drinks ::list)
       (manager:show-message  'Tester
         (<div-class> "alert alert-success" 
           (to-string "あなたの入力内容は以下です．" "name=" name ", age=" age ", thai_curry=" thai_curry ",drinks=" drinks)))))

  (manager:show-message 'Tester (<div-class> "alert alert-warning" "入力結果を受けとるまで，このメッセージが表示されないことに注意して下さい．"))

)


