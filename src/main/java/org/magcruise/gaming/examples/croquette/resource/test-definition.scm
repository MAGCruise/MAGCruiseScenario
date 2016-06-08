(define (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
    (def:player name: 'Farmer type: 'agent class: Farmer)
    (def:player name: 'Factory type: 'human class: Factory
      (list 100 200 300 100 700 300 100 200 300 100 200))

    (def:player name: 'Shop1 type: 'human class: Shop
        (list 100  80 120 100  80 120 100  80 120 100  80)
        (list 200 300 400 200 300 400 200 300 400 200 300))
    (def:player name: 'Shop2 type: 'human class: Shop
        (list  80 100 200 150  80 100 200 150  80 100 200)
        (list 100 200 300 400 100 200 300 400 100 200 300))))


(define (def:after-setup-game-builder builder ::GameBuilder)
  (builder:addDefAssignmentRequests (list 'Factory 'Shop1 'Shop2) (list 'user1_n 'user2_n 'user3_n)))
