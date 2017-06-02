package six.object;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Tile {

	private BufferedImage[] image = new BufferedImage[2];
	
	private int x;
	private int y;
	private int type;
	
	private boolean mouseState = false;
	private boolean tileState = true;
	private boolean tilePlacing = false;
	private boolean tileStateInGame = true;
	private boolean traverseState = false;
	
	private Tile[] adjacentTile = new Tile[6];
	private int numberOfAdjacentTiles = 0;
	
	public Tile(int type) {
		
		this.x = 0;
		this.y = 0;
		this.type = type;
		
		refreshAdjacentTiles();
		
		loadImage(type);
		
	}
	
	public void loadImage(int type) {
		
		try {
			
			if(type == 0) {
			
				image[0] = ImageIO.read(new File("src/six/res/image/tiles/red/placed.png"));
				image[1] = ImageIO.read(new File("src/six/res/image/tiles/red/hovered.png"));
		
			}
			else {
			
				image[0] = ImageIO.read(new File("src/six/res/image/tiles/black/placed.png"));
				image[1] = ImageIO.read(new File("src/six/res/image/tiles/black/hovered.png"));
			
			}
		
		}
		catch(Exception e) {
			
			System.out.println("Problem loading tile image!");
		
		}
		
	}
	
	public void reset() {
		
		x = 0;
		y = 0;
		
		mouseState = false;
		tileState = true;
		tilePlacing = false;
		tileStateInGame = true;
		
		refreshAdjacentTiles();
		
	}
	
	public int getX() {
		
		return x;
		
	}
	
	public int getY() {
		
		return y;
		
	}
	
	public int getType() {
		
		return type;
		
	}
	
	public void moveTo(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public BufferedImage getImage() {
		
		if(mouseState == false) {
			
			return image[0];
			
		}
		else {
			
			return image[1];
			
		}
		
	}
	
	public void setMouseState(boolean state) {
		
		mouseState = state;
		
	}
	
	public boolean getMouseState() {
		
		return mouseState;
		
	}
	
	public void setTileState(boolean state) {
		
		tileState = state;
		
	}
	
	public boolean getTileState() {
		
		return tileState;
		
	}
	
	public void setTilePlacing(boolean placing) {
		
		tilePlacing = placing;
		
	}
	
	public boolean getTilePlacing() {
		
		return tilePlacing;
		
	}
	
	public void refreshAdjacentTiles() {
		
		adjacentTile[0] = adjacentTile[1] = adjacentTile[2] = adjacentTile[3] = adjacentTile[4] = adjacentTile[5] = null;
		numberOfAdjacentTiles = 0;
		
	}
	
	public void setAdjacentTile(int index, Tile tile) {
		
		adjacentTile[index] = tile;
		
	}
	
	public Tile getAdjacentTile(int index) {
		
		return adjacentTile[index];
		
	}
	
	public int traverseLinearly(int index, int type) {
		
		if(adjacentTile[index] == null) {
			
			return 0;
			
		}
		else {

			if(adjacentTile[index].getType() == type) {
				
				return 1 + adjacentTile[index].traverseLinearly(index, type);
				
			}
			else {
				
				return 0;
				
			}
			
		}
		
	}
	
	public int traverseCircularly(int index, int type, int tileVisit) {
		
		if(adjacentTile[index] == null) {
			
			return 0;
			
		}
		else {

			if(adjacentTile[index].getType() == type) {
				
				if(tileVisit < 5) {
					
					if(index < 5) {
						
						return 1 + adjacentTile[index].traverseCircularly(++index, type, ++tileVisit);
						
					}
					else {
						
						return 1 + adjacentTile[index].traverseCircularly(0, type, ++tileVisit);
						
					}
					
				}
				else {
					
					return 0;
					
				}
				
			}
			else {
				
				return 0;
				
			}
			
		}
		
	}
	
	public int traverseTriangularly(int index, int type) {
		
		int match = 0;
		
		if(adjacentTile[index] != null) {
			
			if(adjacentTile[index].getType() == type) {
				
				match++;
				
			}
			
			if(adjacentTile[index].getAdjacentTile(index) != null) {
				
				if(adjacentTile[index].getAdjacentTile(index).getType() == type) {
					
					match++;
					
				}
				
			}
			
		}
		
		if(index < 5) {
			
			if(adjacentTile[index + 1] != null) {
				
				if(adjacentTile[index + 1].getType() == type) {
					
					match++;
					
				}
				
				if(adjacentTile[index + 1].getAdjacentTile(index) != null) {
					
					if(adjacentTile[index + 1].getAdjacentTile(index).getType() == type) {
						
						match++;
						
					}
					
				}
				
				if(adjacentTile[index + 1].getAdjacentTile(index + 1) != null) {
					
					if(adjacentTile[index + 1].getAdjacentTile(index + 1).getType() == type) {
						
						match++;
						
					}
					
				}
				
			}
			
		}
		else {
			
			if(adjacentTile[0] != null) {
				
				if(adjacentTile[0].getType() == type) {
					
					match++;
					
				}
				
				if(adjacentTile[0].getAdjacentTile(index) != null) {
					
					if(adjacentTile[0].getAdjacentTile(index).getType() == type) {
						
						match++;
						
					}
					
				}
				
				if(adjacentTile[0].getAdjacentTile(0) != null) {
					
					if(adjacentTile[0].getAdjacentTile(0).getType() == type) {
						
						match++;
						
					}
					
				}
				
			}
			
		}
		
		return match;
		
	}
	

	public int linearScore(int index, int type) {
		
		if(adjacentTile[index] == null) {
			
			return 0;
			
		}
		else {

			if(adjacentTile[index].getType() == type) {
				
				return 1 + adjacentTile[index].traverseLinearly(index, type);
				
			}
			else {
				
				return -1;
				
			}
			
		}
		
	}
	
	public int circularScore(int index, int type, int tileVisit) {
		
		if(adjacentTile[index] == null) {
			
			return 0;
			
		}
		else {

			if(adjacentTile[index].getType() == type) {
				
				if(tileVisit < 5) {
					
					if(index < 5) {
						
						return 1 + adjacentTile[index].traverseCircularly(++index, type, ++tileVisit);
						
					}
					else {
						
						return 1 + adjacentTile[index].traverseCircularly(0, type, ++tileVisit);
						
					}
					
				}
				else {
					
					return 0;
					
				}
				
			}
			else {
				
				return -1;
				
			}
			
		}
		
	}
	
	public int triangularScore(int index, int type) {
		
		int match = 0;
		
		if(adjacentTile[index] != null) {
			
			if(adjacentTile[index].getType() == type) {
				
				match++;
				
			}
			else {
				
				match--;
				
			}
			
			if(adjacentTile[index].getAdjacentTile(index) != null) {
				
				if(adjacentTile[index].getAdjacentTile(index).getType() == type) {
					
					match++;
					
				}
				else {
					
					match--;
					
				}
				
			}
			
		}
		
		if(index < 5) {
			
			if(adjacentTile[index + 1] != null) {
				
				if(adjacentTile[index + 1].getType() == type) {
					
					match++;
					
				}
				else {
					
					match--;
					
				}
				
				if(adjacentTile[index + 1].getAdjacentTile(index) != null) {
					
					if(adjacentTile[index + 1].getAdjacentTile(index).getType() == type) {
						
						match++;
						
					}
					else {
						
						match--;
						
					}
					
				}
				
				if(adjacentTile[index + 1].getAdjacentTile(index + 1) != null) {
					
					if(adjacentTile[index + 1].getAdjacentTile(index + 1).getType() == type) {
						
						match++;
						
					}
					else {
						
						match--;
						
					}
					
				}
				
			}
			
		}
		else {
			
			if(adjacentTile[0] != null) {
				
				if(adjacentTile[0].getType() == type) {
					
					match++;
					
				}
				else {
					
					match--;
					
				}
				
				if(adjacentTile[0].getAdjacentTile(index) != null) {
					
					if(adjacentTile[0].getAdjacentTile(index).getType() == type) {
						
						match++;
						
					}
					else {
						
						match--;
						
					}
					
				}
				
				if(adjacentTile[0].getAdjacentTile(0) != null) {
					
					if(adjacentTile[0].getAdjacentTile(0).getType() == type) {
						
						match++;
						
					}
					else {
						
						match--;
						
					}
					
				}
				
			}
			
		}
		
		return match;
		
	}
	
	public void updateNumberOfAdjacentTiles(int update) {
		
		numberOfAdjacentTiles += update;
		
	}
	
	public int getNumberOfAdjacentTiles() {
		
		return numberOfAdjacentTiles;
		
	}
	
	public boolean isBridge() {
		
		if(getNumberOfAdjacentTiles() > 4 || getNumberOfAdjacentTiles() == 1) {
			
			return false;
			
		}
		else if(getNumberOfAdjacentTiles() == 4) {
			
			for(int a = 0; a < 6; a++) {
				
				if(adjacentTile[a] != null && adjacentTile[(a + 1) % 6] != null && adjacentTile[(a + 2) % 6] != null && adjacentTile[(a + 3) % 6] != null) {
					
					return false;
					
				}
				
			}
			
			return true;
			
		}
		else if(getNumberOfAdjacentTiles() == 3) {
			
			for(int a = 0; a < 6; a++) {
				
				if(adjacentTile[a] != null && adjacentTile[(a + 1) % 6] != null && adjacentTile[(a + 2) % 6] != null) {
					
					return false;
					
				}
				
			}
			
			return true;
			
		}
		else {
			
			for(int a = 0; a < 6; a++) {
				
				if(adjacentTile[a] != null && adjacentTile[(a + 1) % 6] != null) {
					
					return false;
					
				}
				
			}
			
			return true;
			
		}
		
	}
	
	public boolean getTraverseState() {
		
		return traverseState;
		
	}
	
	public void setTraverseState(boolean state) {
		
		traverseState = state;
		
	}
	
	public void setTileStateInGame(boolean state) {
		
		tileStateInGame = state;
		
	}
	
	public boolean getTileStateInGame() {
		
		return tileStateInGame;
		
	}
	
}
