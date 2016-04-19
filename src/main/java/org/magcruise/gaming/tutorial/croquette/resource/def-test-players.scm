(define (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
    (def:player 'Farmer 'agent Farmer)
    (def:player 'Factory 'human Factory
      (list 100 200 300 100 700 300 100 200 300 100 200))

    (def:player 'Shop1 'human Shop
        (list 100  80 120 100  80 120 100  80 120 100  80)
        (list 200 300 400 200 300 400 200 300 400 200 300))
    (def:player 'Shop2 'human Shop
        (list  80 100 200 150  80 100 200 150  80 100 200)
        (list 100 200 300 400 100 200 300 400 100 200 300))))
