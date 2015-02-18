(load-relative "fisherman.scm")

(define (def:game-scenario)
  (def:player 'Fisherman1 'human)
  (def:player 'Fisherman2 'human)
  (define fishers (list 'Fisherman1 'Fisherman2))

  (def:rounds 3
    (def:parallel-stage 'negotiation
      (def:task  'notify-status)
      (def:players-task fishers 'fisher:input-comment))
    (def:parallel-stage 'fishing
      (def:players-task fishers 'fisher:decide-number-of-fish))
    (def:stage 'calc-fishing
      (def:task 'go-to-fishing)
      (def:task 'cleanup-and-recover))))

