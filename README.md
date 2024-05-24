# Java-Projekt
## Toyscape
**Overview:**
"Toyscape" is a strategic board game where the player controls a hero to defeat enemies and navigate through obstacles. The player has a limited number of actions per round to move and attack.

### Game Board
- The game board is a 12x12 grid.
- The grid contains various elements:
  - **Obstacles:**
    - Stones 
    - Trees 
  - **Enemies:**
    - Enemies with 1 life 
    - Enemies with 2 lives 
  - **Hero:**
    - The player controls the hero

## Objectives
- Defeat all enemies on the board.
- Move strategically to avoid obstacles and enemy attacks.
- Do not lose all lives (hearts) to enemy attacks.

## Game Rules
1. **Player Actions:**
   - The player has 2 actions per round.
   - Actions can be:
     - Moving one tile in any direction (up, down, left, right).
     - Attacking an adjacent enemy.
2. **Movement:**
   - The hero can only move to free tiles (no obstacles or occupied tiles).
3. **Attacking:**
   - Attacks can only be made on adjacent tiles.
   - An attack removes one life from an enemy. Enemies with 2 lives require two attacks.
4. **Enemy Actions:**
   - Enemies move one tile per round towards the hero's closest position.
   - Enemies will attack if the hero comes to an adjacent tile.
5. **Rounds and Lives:**
   - The game consists of multiple rounds.
   - The hero starts with 3 lives (hearts). Each enemy attack causes the hero to lose one life.

## End of Game
- The game ends when all enemies are defeated (victory) or the hero loses all lives (defeat).

## Controls
- The player selects actions by inputting the move corresponding  or attack commands.

Example:
- Move up: `W`
- Attack: `left click on a near by enemy`

## Visualization
- Each round, the game board updates to show new positions and life states of the hero and enemies.
- The remaining actions and lives of the hero are also displayed.
- The round timer goes up by one.

---