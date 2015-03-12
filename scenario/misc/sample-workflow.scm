(define (def:game-scenario)
  (def:player 'HumanPlayer1 'human)
  (def:player 'HumanPlayer2 'human)
  (def:player 'HumanPlayer3 'human)

  (define (c1 ctx ::Context) (eqv? ctx:roundnum 0))
  (define (c2 ctx ::Context) (eqv? ctx:roundnum 1))

  ;; r1: H1 → H1, H3, H2 → task
  ;; r2: H1 → H1 → task
  ;; r3: H1 → H2 → task
  (def:rounds 3
    (def:stage 'test
      (def:task 'HumanPlayer1 'vote))
    (def:exor-stage 'vote
      (list c1 c2)
      (def:parallel-stage 'h1_2
        (def:parallel-stage 'h1_3
          (def:task 'HumanPlayer1 'vote)
          (def:task 'HumanPlayer3 'vote))
        (def:task 'HumanPlayer2 'vote))
      (def:task 'HumanPlayer1 'vote)
      (def:task 'HumanPlayer2 'vote))
    (def:stage 'dist
      (def:task 'distribution))))

(define items '("アイテムX" "アイテムY"))

(define (vote ctx ::Context self ::Player)
  (ui:input-request self:name
    (ui:form
      "1000円のアイテムXと100円のアイテムYがあります．
      あなたの他に2人プレーヤがいて，他のプレーヤと異なるアイテムを選べば，そのアイテムを貰うことができます．
      もし，一緒のアイテムを選んだ場合は，何も貰うことは出来ません．どちらのアイテムを選びますか？"
      (ui:radio-input "アイテムの選択" 'item (car items) items items))
    (lambda (item)
      (self:set 'item item))))

(define (distribution ctx ::Context)
  (define select-first (filter (lambda (p ::Player) (eqv? (p:get 'item) (items 0))) ctx:players:all))
  (define select-second (filter (lambda (p ::Player) (eqv? (p:get 'item) (items 1))) ctx:players:all))
  (define minority
    (if (< (length select-first) (length select-second)) select-first select-second))
  (ui:show-message 'all
    (html:p "結果は以下です"
    (apply html:ul
      (map
        (lambda (p ::Player) (to-string p:name " → " (p:get 'item)))
        ctx:players:all))))
  (log:debug (ln) minority)
  (for-each
    (lambda (p ::Player)
      (ui:show-message 'all p:name "が" (p:get 'item) "を手に入れました．"))
    minority)
  (ui:show-message 'all "ラウンド" ctx:roundnum "が終了しました"))
