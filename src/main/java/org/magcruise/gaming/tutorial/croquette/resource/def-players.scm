(define (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
    (def:player 'Farmer 'agent Farmer)
    (def:player 'Factory 'human Factory
      (list 300 300 300 300 300 300 300 300 300 300 300))

    (def:player 'Shop1 'human Shop
        (list 100 100 100 100 100 100 100 100 100 100 100)
        (list 400 400 400 400 400 400 400 400 400 400 400))
    (def:player 'Shop2 'human Shop
        (list 150 150 150 150 150 150 150 150 150 150 150)
        (list 150 150 150 150 150 150 150 150 150 150 150))))
