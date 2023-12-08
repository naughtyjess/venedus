package de.pxscxl.spigot.buildffa.manager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.pxscxl.origin.spigot.Origin;
import de.pxscxl.origin.spigot.api.OriginPlayer;
import de.pxscxl.origin.spigot.api.inventory.InventoryBuilder;
import de.pxscxl.origin.spigot.api.inventory.ItemStackBuilder;
import de.pxscxl.origin.spigot.api.manager.OriginManager;
import de.pxscxl.origin.utils.session.Session;
import de.pxscxl.origin.utils.session.query.QueryStatement;
import de.pxscxl.origin.utils.session.query.UpdateStatement;
import de.pxscxl.spigot.buildffa.BuildFFA;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.IntStream;

public class KitManager implements Listener {

    @Getter
    private static KitManager instance;

    private final UpdateStatement create;
    private final UpdateStatement insert;
    private final QueryStatement exist;

    private final QueryStatement getInventory;
    private final UpdateStatement setInventory;

    @Getter
    private final HashMap<UUID, JsonObject> inventories = new HashMap<>();

    public KitManager() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, BuildFFA.getInstance());
        Session session = Origin.getInstance().getSession();

        create = session.prepareUpdateStatement("CREATE TABLE IF NOT EXISTS buildffa_inventory (uuid VARCHAR(36), inventory TEXT)");
        insert = session.prepareUpdateStatement("INSERT INTO buildffa_inventory (uuid, inventory) VALUES (?, ?)");
        exist = session.prepareQueryStatement("SELECT uuid FROM buildffa_inventory WHERE uuid = ?");

        getInventory = session.prepareQueryStatement("SELECT * FROM buildffa_inventory WHERE uuid = ?");
        setInventory = session.prepareUpdateStatement("UPDATE buildffa_inventory SET inventory = ? WHERE uuid = ?");

        create();
    }

    public void create() {
        try {
            create.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(UUID uuid) {
        try {
            JsonObject object = new JsonObject();
            object.addProperty("GOLD_SWORD", 0);
            object.addProperty("STICK", 1);
            object.addProperty("BOW", 2);
            object.addProperty("ENDER_PEARL", 3);
            object.addProperty("WEB", 4);
            object.addProperty("SANDSTONE", 5);
            object.addProperty("ARROW", 8);

            insert.execute(uuid, object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exist(UUID uuid) {
        try {
            ResultSet resultSet = exist.execute(uuid);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void loadInventory(UUID uuid) {
        try {
            ResultSet resultSet = getInventory.execute(uuid);
            if (resultSet.next()) {
                inventories.put(uuid, (JsonObject) new JsonParser().parse(resultSet.getString("inventory")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setInventory(UUID uuid, JsonObject object) {
        try {
            setInventory.execute(object, uuid);

            inventories.put(uuid, object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setKitItems(OriginPlayer player) {
        player.getInventory().clear();

        player.getInventory().setHelmet(new ItemStackBuilder(Material.LEATHER_HELMET).setColor(Color.RED).setUnbreakable(true).build());
        player.getInventory().setChestplate(new ItemStackBuilder(Material.CHAINMAIL_CHESTPLATE).setUnbreakable(true).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        player.getInventory().setLeggings(new ItemStackBuilder(Material.LEATHER_LEGGINGS).setColor(Color.RED).setUnbreakable(true).build());
        player.getInventory().setBoots(new ItemStackBuilder(Material.LEATHER_BOOTS).setColor(Color.RED).setUnbreakable(true).build());

        Arrays.asList(
                new ItemStackBuilder(Material.GOLD_SWORD).setUnbreakable(true).enchant(Enchantment.DAMAGE_ALL, 1).build(),
                new ItemStackBuilder(Material.STICK).enchant(Enchantment.KNOCKBACK, 1).build(),
                new ItemStackBuilder(Material.BOW).setUnbreakable(true).enchant(Enchantment.ARROW_DAMAGE, 1).build(),
                new ItemStackBuilder(Material.ENDER_PEARL).build(),
                new ItemStackBuilder(Material.WEB).amount(3).build(),
                new ItemStackBuilder(Material.SANDSTONE).amount(64).build(),
                new ItemStackBuilder(Material.ARROW).amount(16).build()
        ).forEach(itemStack -> player.getInventory().setItem(inventories.get(player.getUniqueId()).get(itemStack.getType().name()).getAsInt(), itemStack));
    }

    public void openGUI(OriginPlayer player) {
        InventoryBuilder inventory = new InventoryBuilder(1, "§8" + player.language("Inventar", "Inventory"));

        Arrays.asList(
                new ItemStackBuilder(Material.GOLD_SWORD).setUnbreakable(true).enchant(Enchantment.DAMAGE_ALL, 1).build(),
                new ItemStackBuilder(Material.STICK).enchant(Enchantment.KNOCKBACK, 1).build(),
                new ItemStackBuilder(Material.BOW).setUnbreakable(true).enchant(Enchantment.ARROW_DAMAGE, 1).build(),
                new ItemStackBuilder(Material.ENDER_PEARL).build(),
                new ItemStackBuilder(Material.WEB).amount(3).build(),
                new ItemStackBuilder(Material.SANDSTONE).amount(64).build(),
                new ItemStackBuilder(Material.ARROW).amount(16).build()
        ).forEach(itemStack -> inventory.setItem(inventories.get(player.getUniqueId()).get(itemStack.getType().name()).getAsInt(), itemStack));

        player.openInventory(inventory);
        player.getInventory().clear();
        player.playSound(player.getLocation(), Sound.CLICK, 10F, 10F);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        OriginPlayer player = OriginManager.getInstance().getPlayer((Player) event.getWhoClicked());

        if (event.getView().getTitle().equals("§8" + player.language("Inventar", "Inventory"))) {
            if (event.getClickedInventory().getHolder() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        OriginPlayer player = OriginManager.getInstance().getPlayer((Player) event.getPlayer());

        if (event.getView().getTitle().equals("§8" + player.language("Inventar", "Inventory"))) {
            List<ItemStack> contents = new ArrayList<>();
            for (ItemStack content : event.getInventory().getContents()) {
                if (content != null) contents.add(content);
            }
            if (contents.size() == 7) {
                player.sendMessage(
                        BuildFFA.getInstance().getPrefix() + "§7Dein Inventar wurde §aerfolgreich §7gespeichert",
                        BuildFFA.getInstance().getPrefix() + "§7Your inventory was §asuccessfully §7saved"
                );
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 10F, 10F);

                JsonObject object = new JsonObject();
                IntStream.range(0, 9).forEach(value -> {
                    ItemStack item = event.getInventory().getItem(value);
                    if (item != null) {
                        object.addProperty(item.getType().name(), value);
                    }
                });
                Bukkit.getScheduler().runTaskAsynchronously(BuildFFA.getInstance(), () -> setInventory(player.getUniqueId(), object));
            } else {
                player.sendMessage(
                        BuildFFA.getInstance().getPrefix() + "§7Est ist ein §cFehler §7beim Speichern aufgetreten",
                        BuildFFA.getInstance().getPrefix() + "§7An §cerror §7occurred while saving"
                );
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10F, 10F);
            }
            Bukkit.getScheduler().runTaskLater(BuildFFA.getInstance(), () -> {
                player.getInventory().clear();
                GameManager.getInstance().setSpawnItems(player);
            }, 1);
        }
    }
}
