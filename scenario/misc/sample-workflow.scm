(define (def:game-scenario)
  (def:player 'HumanPlayer1 'human)
  (def:player 'HumanPlayer2 'human)
  (def:player 'HumanPlayer3 'human)

  (def:rounds 2
    (def:stage 'test
      (def:task 'HumanPlayer1 'vote1))
    (def:exor-stage 'vote
      (lambda (ctx ::Context) (eqv? ctx:roundnum 0))
      (def:parallel-stage 'h1_2
        (def:parallel-stage 'h1_3
          (def:task 'HumanPlayer1 'vote2)
          (def:task 'HumanPlayer2 'vote2))
        (def:task 'HumanPlayer3 'vote2))
      (def:stage 'h1_3
        (def:task 'HumanPlayer1 'vote3)
        (def:task 'HumanPlayer2 'vote3)))
    (def:stage 'dist
      (def:task 'distribution))))


(define (vote1 ctx ::Context self ::Player)
  (manager:show-message self:name 1))
  
(define (vote2 ctx ::Context self ::Player)
  (manager:show-message self:name 2))

(define (vote3 ctx ::Context self ::Player)
  (manager:show-message self:name 3))
  

(define (distribution ctx ::Context)
  (manager:show-message 'all "dist"))
