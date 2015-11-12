(load-relative "fisherman.scm")

(define (def:setup-game-builder game-builder ::GameBuilder)
  (def:player 'Fisherman1 'human SimplePlayer)
  (def:player 'Fisherman2 'human SimplePlayer)
  (define fishers (list 'Fisherman1 'Fisherman2))

  (def:rounds 3
    (def:parallel-stage 'negotiation
      (def:task  'notify-status)
      (def:players-task fishers 'fisher:comment))
    (def:parallel-stage 'fishing
      (def:players-task fishers 'fisher:decide-number-of-fish))
    (def:stage 'calc-fishing
      (def:task 'go-to-fishing)
      (def:task 'cleanup-and-recover))))

