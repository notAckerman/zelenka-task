package org.example;

import org.example.api.BookApi;
import org.example.api.GenreApi;
import org.example.model.Book;
import org.example.model.Genre;
import org.example.utils.Parser;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Графический интерфейс
public class Main {
    private static final BookApi bookApi = new BookApi();
    private static final GenreApi genreApi = new GenreApi();
    private static DefaultListModel<String> currListModel = getStringDefaultListModel(bookApi.getBooks());

    // С помощью StreamAPI создаём коллекцию, состоящую из названий жанров
    private static List<String> genresList = Parser.parseGenres(genreApi.getGenres());

    public static void main(String[] args) {
        // Создание элементов графического интерфейса
        JFrame frame = new JFrame("Book manager");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JList<String> bookJList = getScrollPanel(bookApi.getBooks());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(getSearchPanel(bookJList));
        topPanel.add(getFilterPanel(bookJList));

        // Создаем кнопку "Add New Book"
        JButton addButton = new JButton("Add New Book");
        addButton.addActionListener(e -> {
            // Создаем панель для ввода данных о новой книге
            createAddBookPanel(frame, bookJList);
        });
        topPanel.add(addButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(bookJList), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void createAddBookPanel(JFrame frame, JList<String> bookJList) {
        // Текстовые поля для ввода информации о книге
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        inputPanel.add(new JLabel("Author:"));
        JTextField authorField = new JTextField();
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Title:"));
        JTextField titleField = new JTextField();
        inputPanel.add(titleField);

        // Создаётся выпадающий список жанров
        JComboBox<String> genreComboBox = new JComboBox<>(genresList.toArray(new String[0]));
        genreComboBox.setEditable(true); // Разрешаем пользовательский ввод
        inputPanel.add(new JLabel("Genre:"));
        inputPanel.add(genreComboBox);

        inputPanel.add(new JLabel("Description:"));
        JTextField descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        // Показываем диалоговое окно для ввода данных
        int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Book Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Получаем введенные данные
            String author = authorField.getText();
            String title = titleField.getText();
            String genre = (String) genreComboBox.getSelectedItem();
            String description = descriptionField.getText();


            // Проверяем, что все поля заполнены
            if (author.isEmpty() || title.isEmpty() || genre.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Book book = new Book(title, author, description, new Genre(genre));
            bookApi.addBook(book);
            JOptionPane.showMessageDialog(frame, "Book successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Обновляем списки
            genresList = Parser.parseGenres(genreApi.getGenres());
            currListModel = getStringDefaultListModel(bookApi.getBooks());
            bookJList.setModel(currListModel);
            bookJList.updateUI();
        }
    }

    @NotNull
    private static JPanel getFilterPanel(JList<String> bookJList) {
        // Парсим список в элементы в выпадающем списке
        JComboBox<String> categoryComboBox = new JComboBox<>(genresList.toArray(new String[0]));

        // Обработчик выбора одного из жанров
        // Получает список книг, жанр которых совпадает с выбранным из списка жанров
        categoryComboBox.addActionListener(e -> {
            String genre = (String) categoryComboBox.getSelectedItem();
            currListModel = getStringDefaultListModel(bookApi.searchBooksByGenre(genre));
            // Обновляем список книг
            bookJList.setModel(currListModel);
            bookJList.updateUI();

        });

        // Добавляем элементы в панель (Вызываются только при создании интерфейса)
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter by Category: "));
        filterPanel.add(categoryComboBox);
        return filterPanel;
    }

    @NotNull
    private static JPanel getSearchPanel(JList<String> bookJList) {
        // Поисковая строка, а также кнопка (создаётся в методе getSearchButton(...) ) для подтверждения поиска
        JPanel topPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField(35);
        JButton searchButton = getSearchButton(searchField, bookJList);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);
        return topPanel;
    }

    private static JButton getSearchButton(JTextField searchField, JList<String> bookJList) {
        JButton searchButton = new JButton("Search");

        // Обработка нажатия на кнопку поиска
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText();

            // Обновление списка
            currListModel = getStringDefaultListModel(bookApi.searchBooks(keyword));
            bookJList.setModel(currListModel);
            bookJList.updateUI();
        });
        return searchButton;
    }

    @NotNull
    private static DefaultListModel<String> getStringDefaultListModel(List<Book> searchResult) {
        // Метод для обновления книг в списке
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Book book : searchResult) {
            listModel.addElement("Id " + book.getId() + "  " + book.getAuthor() + " : " + book.getName());
        }
        return listModel;
    }


    // Метод возвращает панель, в которой отображаются книги
    public static JList<String> getScrollPanel(List<Book> books) {
        // Обновляем
        currListModel = getStringDefaultListModel(books);

        // Параметры ячеек
        JList<String> bookJList = new JList<>(currListModel);
        bookJList.setFixedCellHeight(60);
        bookJList.setFixedCellWidth(300);

        // Всплывающее меню, в котором пользователь может получить подробную информация
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem a1 = new JMenuItem("Подробная информация");
        a1.addActionListener(e -> {
            String text = bookJList.getSelectedValue();
            Book book = bookApi.getBook(Parser.getId(text));
            showBookDetailsWindow(book);

        });
        popupMenu.add(a1);

        // Всплывающее меню, в котором пользователь может удалить книгу
        JMenuItem a2 = new JMenuItem("Удалить");
        a2.addActionListener(e -> {
            String text = bookJList.getSelectedValue();
            String id = Parser.getId(text);

            // Удаляем книгу из списка
            Book book = bookApi.getBook(id);
            int index = currListModel.indexOf("Id " + book.getId() + "  " + book.getAuthor() + " : " + book.getName());
            currListModel.remove(index);

            // Удаляем книгу из БД
            bookApi.deleteBook(id);
            bookJList.updateUI();
        });
        popupMenu.add(a2);

        bookJList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me) && !bookJList.isSelectionEmpty() && bookJList.locationToIndex(me.getPoint()) == bookJList.getSelectedIndex()) {
                    popupMenu.show(bookJList, me.getX(), me.getY());
                }
            }
        });

        return bookJList;
    }


    private static void showBookDetailsWindow(Book book) {
        // Создаём всплывающее окно, где содержится подробная информация о книге
        JFrame detailsFrame = new JFrame("Book Details");
        detailsFrame.setSize(500, 400);
        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setText("Title: " + book.getName() + "\n"
                + "Author: " + book.getAuthor() + "\n"
                + "Description: " + book.getDescription() + "\n"
                + "Genre: " + book.getGenre().getName());
        detailsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(detailsTextArea);
        detailsFrame.add(scrollPane, BorderLayout.CENTER);
        detailsFrame.setResizable(false);
        detailsFrame.setVisible(true);
    }
}
