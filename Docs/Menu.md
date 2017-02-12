<hr/>
<h1>Introduction</h1>
<p>Pour gérer les menus plusieurs classe sont disponible :
	<ul>
		<li><strong>ItemBuilder.java</strong></li>
		<li><strong>Menu.java</strong></li>
		<li><strong>MenuRecurcive.java</strong></li>
	</ul>
	<br/>
	Ces classes permetent de crée des menus dynamique
</p>
<h1>Comment les utiliser</h1>

<h2>Utiliser la classe ItemBuilder.java</h2>
<p>C'est une classe builder qui permet de crée un objet quelconque.
<br/>On crée une configuration de paramètres pour l'objet qui va être retourné par la méthode <strong>get()</strong>.
</p>
<h3>Exemple d'utilisation</h3>
```java
ItemBuilder itemBuilder = new Item(Material.STONE); //Création de l'item STONE
itemBuilder.withAmount(5); //On configure le nombre d'item à 5
itemBuilder.withAdvancedEventHandlerList(new AdvancedEventHandler<InventoryClickEvent>() {
    @Override
    public void onEvent(InventoryClickEvent event) {
        if(event.getWhoClicked() instanceof Player){
            Player player = ((Player) event.getWhoClicked()).getPlayer();
            player.sendMessage("Vous avez cliquer");
        }
    }

    @Override
    public Class<InventoryClickEvent> getEventClass() {
        return InventoryClickEvent.class;
    }
) //Appel la fonction onEvent quand un joueur clique sur l'item
itemBuild.get(); //On récupère l'itemStack crée
```
<h2>Utliser la classe Menu.java</h2>
```java
publi class MenuExemple extends Menu {
	pulic MenuExemple(Player player){
        * @param ids byte[] ids color of glass	* @param menu Inventory menu create menu bukkit
	    * @param model int[][] mode [1][ 1 : Position of menu 2 : Position of table ids
	    super(null, Bukkit.createInvento(null, 18, "Titre", null, plugin);
	    super.menu.setItem(index, new ItBuilder(Material.SWORD).withEnchant(Enchantment.FIRE_ASPECT, 2).hideEnchant(true).get());
	    player.openInventory(super.menu)	}	/**
	    * Event des cliques dans l'inventaire
	    */
	    @Override //Appel la fonction onEvent quand un joueur clique sur l'itemride
	    public void interractInventory(InventoryClickEvent e) {
		
	    }
}
```

<h2>Utliser la classe MenuRecurcive.java</h2>
```java
public class MenuExemple extends MenuRecurcive {
	private MenuExemple(Player player, ArrayList<Item> list){
		* @param ids byte[] ids color of glass
		* @param menu Inventory menu create menu bukkit
		* @param model int[][] mode [1][2] 1 : Position of menu 2 : Position of table ids
		* @param list ArrayList<Item> list List item à insérer
		* @param lang String lang du joueur
		* @param lineSize int longeur d'une ligne d'insersion des items
		* @param start int index de démarrage d'insersion des items
		* @param exclude int[] index exclue pour l'insertion des items
		* @param type Type Type de menu
		* @param plugin JavaPlugin instance du plugin
		super(new byte[] {14, 5}, Bukkit.createInventory(null, 45, "Titre"), new int[][] {{0, 0}, {1, 0}, {2, 1}, {3, 0}, {5, 0}, {6, 1}, {7, 0}, {8, 0}, {9, 0}, {17, 0}, {18, 1}, {26, 1}, {27, 0}, {35, 0}, {37, 0}, {38, 1}, {39, 0}, {40, 0}, {41, 0}, {42, 1}, {43, 0}, {44, 0}}, list, PlayerData.getPlayerData(player.getName()).getLang(), 7 , 9,  new int[] {13}, Type.DYNAMIQUE, plugin);
		initMenu(player, 1);
		player.openInventory(super.menu);
	}

	@Override
	public void initMenu(Player player, int page){
		super.menu.clear();
		super.menu.setItem(new ItemBuilder(Material.SWORD).withEnchant(Enchantment.FIRE_ASPECT, 2).hideEnchant(true).get());
        super.getPage(page);
        player.updateInventory();
	}
	
	public static void createMenu(Player player){
		ArrayList<Item> list = new ArrayList<>();
		list.add(new Item(Material.WOOD));
		new MenuExemple(player, list);
    }

	/**
	 * Event des cliques dans l'inventaire
	 */
	@Override
	public void interractInventory(InventoryClickEvent e){
		ItemStack itemStack = e.getCurrentItem();
		Player player = (Player) e.getWhoClicked();
		switch (itemStack.getType()) {
			case ARROW:
				player.closeInventory();
				break;
			case PAPER:
				this.initMenu(player, page+1); //Page suivante
				break;
			case EMPTY_MAP:
				this.initMenu(player, page-1); //Page précédente
				break;
			default:
				break;
		}
	}

}
```
