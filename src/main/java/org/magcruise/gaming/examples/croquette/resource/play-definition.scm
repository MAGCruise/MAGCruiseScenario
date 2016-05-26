(define (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
    (def:player 'Farmer 'agent Farmer)
    (def:player 'Factory 'human Factory
      (list #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null))

    (def:player 'Shop1 'human Shop
        (list #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null)
        (list #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null))
    (def:player 'Shop2 'human Shop
        (list #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null)
        (list #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null))))
