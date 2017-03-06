package fr.herezia.api.objects;

import fr.herezia.api.data.Data;
import lombok.Getter;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;
import fr.herezia.api.lang.Lang;
import fr.herezia.api.lang.LangSetting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Project Hub Created by Vorps on 01/02/2016 at 01:41.
 */
public class BookHelp {

    private ItemStack book;
    private @Getter HashMap<String, fr.herezia.api.menu.ItemBuilder> item;
    private HashMap<String, String> label;
    private @Getter int level;
    private HashMap<String, ArrayList<String>> values;
    private boolean state;

    public BookHelp(ResultSet result, boolean state, Database database) throws SqlException {
        this.state = state;
        if(state) this.item = new HashMap<>();
        else this.label = new HashMap<>();
        this.values = new HashMap<>();
        this.book = new ItemStack(Material.WRITTEN_BOOK, 1);
        this.level = Database.SERVER.getDatabase().getInt(result, 4);
        BookMeta meta = (BookMeta) this.book.getItemMeta();
        meta.setAuthor(Database.SERVER.getDatabase().getString(result, 2));
        this.book.setItemMeta(meta);
        if (state) for(LangSetting langSetting : LangSetting.getListLangSetting().values()) this.item.put(langSetting.getName(), Item.getItem(Database.SERVER.getDatabase().getString(result, 3), langSetting.getName()));
        else for(LangSetting langSetting : LangSetting.getListLangSetting().values()) this.label.put(langSetting.getName(), Lang.getMessage(Database.SERVER.getDatabase().getString(result, 3), langSetting.getName()));
        ResultSet results = database.getDatabase().getData("book_setting", "bs_book = '"+ Database.SERVER.getDatabase().getString(result, 1)+"' ");
        try {
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
                ArrayList<String> pages = new ArrayList<>();
                while(results.next()) pages.add(Lang.getMessage(Database.SERVER.getDatabase().getString(results, 3), langSetting.getName()));
                this.values.put(langSetting.getName(), pages);
            }
        } catch (SQLException e){e.printStackTrace();}
        if(state) for(LangSetting langSetting : LangSetting.getListLangSetting().values()) BookHelp.bookHelpList.put(this.item.get(langSetting.getName()).get().getItemMeta().getDisplayName(), this);
        else for(LangSetting langSetting : LangSetting.getListLangSetting().values()) BookHelp.bookHelpList.put(this.label.get(langSetting.getName()), this);
    }

    public ItemStack getBook(String lang){
        BookMeta meta = (BookMeta) this.book.getItemMeta();
        meta.setPages(this.values.get(lang));
        String title;
        if(this.state) title = this.item.get(lang).get().getItemMeta().getDisplayName();
        else title = this.label.get(lang);
        meta.setTitle(title);
        this.book.setItemMeta(meta);
        return this.book;
    }

    private static HashMap<String, BookHelp> bookHelpList;
    private static @Getter TreeMap<String ,BookHelp> trieBookHelp;

    static {
        BookHelp.bookHelpList = new HashMap<>();
        BookHelp.trieBookHelp = new TreeMap<>(new ComparatorBookHelp(BookHelp.bookHelpList));
        Data.getInstance().loadBookHelp();
    }

    public static HashMap<String, BookHelp> getBookList(){
        return BookHelp.bookHelpList;
    }

    public static void trieBookHelp(){
        BookHelp.trieBookHelp.clear();
        BookHelp.trieBookHelp.putAll(bookHelpList);
    }

    private static class ComparatorBookHelp implements Comparator<String> {
        Map<String, BookHelp> base;
        private ComparatorBookHelp(Map<String, BookHelp> base) {
            this.base = base;
        }

        @Override
		public int compare(String a, String b) {
            return this.base.get(a).level >= this.base.get(b).level ? 1 : -1;
        }
    }

    public static BookHelp getBookHelp(String nameBook){
        return bookHelpList.get(nameBook);
    }

    public static void clear(){
        bookHelpList.clear();
    }
}
