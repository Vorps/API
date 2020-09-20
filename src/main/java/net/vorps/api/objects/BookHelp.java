package net.vorps.api.objects;

import lombok.Getter;

import net.vorps.api.data.DataCore;
import net.vorps.api.databases.DatabaseManager;
import net.vorps.api.lang.Lang;
import net.vorps.api.lang.LangSetting;
import net.vorps.api.menu.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project Hub Created by Vorps on 01/02/2016 at 01:41.
 */
public class BookHelp{

    private final String name;
    private final String author;
    private final String label;
    private final ArrayList<String> pages;

    public BookHelp(ResultSet result, DatabaseManager database) throws SQLException {
        this.name =  result.getString(1);
        this.author = result.getString(2);
        this.label = result.getString(3);
        this.pages = new ArrayList<>();
        ResultSet book_setting = database.getData("book_setting", "bs_book = '"+this.name+"' ");
        try {
            while(book_setting.next()) this.pages.add(book_setting.getString("bs_value"));
        } catch (SQLException e){e.printStackTrace();}
        BookHelp.bookHelpList.put(this.name, this);
    }

    public ItemStack getBook(String lang){
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        if(meta != null){
            meta.setPages(this.pages.stream().map(e ->Lang.getMessage(e, lang)).collect(Collectors.toList()));
            meta.setTitle(Item.isItem(this.label) ? Item.getItem(this.label, lang).getName() : Lang.getMessage(this.label, lang));
            meta.setAuthor(this.author);
            book.setItemMeta(meta);
        }
        return book;
    }

    public ItemBuilder getIcon(String lang){
        return Item.getItem(this.label, lang).withUUID(this.name);
    }

    private static HashMap<String, BookHelp> bookHelpList;

    static {
        BookHelp.bookHelpList = new HashMap<>();
        DataCore.loadBookHelp();
    }

    public static BookHelp getBookHelp(String name){
        return bookHelpList.get(name);
    }

    public static List<String> getBookHelps(){
        return new ArrayList<>(BookHelp.bookHelpList.keySet());
    }
    public static void clear(){
        bookHelpList.clear();
    }
}
