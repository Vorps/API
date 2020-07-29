package net.vorps.api.objects;

import lombok.Getter;

import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.data.DataCore;
import net.vorps.api.databases.Database;
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

/**
 * Project Hub Created by Vorps on 01/02/2016 at 01:41.
 */
public class BookHelp{

    private final ItemStack book;
    private @Getter HashMap<String, ItemBuilder> item;
    private HashMap<String, String> label;
    private @Getter
    final int level;
    private final HashMap<String, ArrayList<String>> values;
    private final boolean state;

    public BookHelp(ResultSet result, boolean state, DatabaseManager database) throws SQLException, SqlException {
        this.state = state;
        if(state) this.item = new HashMap<>();
        else this.label = new HashMap<>();
        this.values = new HashMap<>();
        this.book = new ItemStack(Material.WRITTEN_BOOK, 1);
        this.level = result.getInt( 4);
        BookMeta meta = (BookMeta) this.book.getItemMeta();
        meta.setAuthor(result.getString(2));
        this.book.setItemMeta(meta);
        if (state) for(LangSetting langSetting : LangSetting.getListLangSetting().values()) this.item.put(langSetting.getName(), Item.getItem(result.getString(3), langSetting.getName()));
        else for(LangSetting langSetting : LangSetting.getListLangSetting().values()) this.label.put(langSetting.getName(), Lang.getMessage(result.getString(3), langSetting.getName()));
        ResultSet results = database.getData("book_setting", "bs_book = '"+ result.getString(1)+"' ");
        try {
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
                ArrayList<String> pages = new ArrayList<>();
                while(results.next()) pages.add(Lang.getMessage(result.getString(3), langSetting.getName()));
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
        DataCore.getInstance().loadBookHelp();
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
