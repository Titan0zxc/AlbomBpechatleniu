package ui;

import model.Slaid.Slaid;
import model.Slaid.IzobrazhenieSlaid;
import kollektsii.SlaidSpisok;
import kollektsii.SlaidKolleksiya;
import builders.ViewState;
import fabriki.SlaidFabrika;
import fabriki.IzobrazhenieSlaidFabrika;
import servisi.FileServis;
import servisi.KonfigServis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class GlavnoeOkno extends JFrame {

    private SlaidKolleksiya kollektsiya;
    private ViewState sostoyanie;
    private SlaidFabrika fabrika;
    private FileServis fileServis;
    private KonfigServis konfigServis;

    // Компоненты UI
    private JPanel panelSlaida;
    private JLabel labelIzobrazhenie;
    private JTextArea textAreaZametka;
    private JButton btnPred;
    private JButton btnSled;
    private JButton btnPerviy;
    private JButton btnPosledniy;
    private JLabel labelProgress;
    private JComboBox<String> comboAnimatsii;
    private JSlider sliderSkorost;
    private JCheckBox checkZametki;

    public GlavnoeOkno() {
        kollektsiya = new SlaidSpisok();
        fabrika = new IzobrazhenieSlaidFabrika();
        fileServis = new FileServis();
        konfigServis = new KonfigServis();
        sostoyanie = ViewState.sozdatNachalnoeSostoyanie();

        initComponents();
        obnovitInterfeis();
    }

    private void initComponents() {
        setTitle("AlbomBpechatleniu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Главный контейнер
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Панель слайда (центр)
        panelSlaida = new JPanel(new BorderLayout());
        panelSlaida.setBackground(Color.WHITE);
        panelSlaida.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        labelIzobrazhenie = new JLabel("Загрузите изображение", SwingConstants.CENTER);
        labelIzobrazhenie.setFont(new Font("Arial", Font.PLAIN, 16));
        panelSlaida.add(labelIzobrazhenie, BorderLayout.CENTER);

        // Панель заметки (низ)
        JPanel panelZametka = new JPanel(new BorderLayout());
        panelZametka.setBorder(BorderFactory.createTitledBorder("Заметка к слайду"));

        textAreaZametka = new JTextArea(5, 20);
        textAreaZametka.setLineWrap(true);
        textAreaZametka.setWrapStyleWord(true);
        panelZametka.add(new JScrollPane(textAreaZametka), BorderLayout.CENTER);

        // Панель управления (верх)
        JPanel panelUpravlenie = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnZagruzit = new JButton("Загрузить изображения");
        btnZagruzit.addActionListener(e -> zagruzitIzobrazheniya());

        JButton btnDobavitTekst = new JButton("Добавить текст");
        btnDobavitTekst.addActionListener(e -> dobavitTekst());

        JButton btnDobavitSmailik = new JButton("Добавить смайлик");
        btnDobavitSmailik.addActionListener(e -> dobavitSmailik());

        JButton btnSohranit = new JButton("Сохранить слайд");
        btnSohranit.addActionListener(e -> sohranitSlaid());

        panelUpravlenie.add(btnZagruzit);
        panelUpravlenie.add(btnDobavitTekst);
        panelUpravlenie.add(btnDobavitSmailik);
        panelUpravlenie.add(btnSohranit);

        // Панель навигации (низ)
        JPanel panelNavigatsiya = new JPanel(new FlowLayout(FlowLayout.CENTER));

        btnPerviy = new JButton("<< Первый");
        btnPerviy.addActionListener(e -> perekluchitSlaid(0));

        btnPred = new JButton("< Предыдущий");
        btnPred.addActionListener(e -> predidushiySlaid());

        labelProgress = new JLabel("0 / 0");
        labelProgress.setFont(new Font("Arial", Font.BOLD, 14));

        btnSled = new JButton("Следующий >");
        btnSled.addActionListener(e -> sleduyushiySlaid());

        btnPosledniy = new JButton("Последний >>");
        btnPosledniy.addActionListener(e -> perekluchitSlaid(kollektsiya.razmer() - 1));

        panelNavigatsiya.add(btnPerviy);
        panelNavigatsiya.add(btnPred);
        panelNavigatsiya.add(labelProgress);
        panelNavigatsiya.add(btnSled);
        panelNavigatsiya.add(btnPosledniy);

        // Панель настроек (правый бок)
        JPanel panelNastroiki = new JPanel(new GridLayout(5, 1, 5, 5));
        panelNastroiki.setBorder(BorderFactory.createTitledBorder("Настройки показа"));

        checkZametki = new JCheckBox("Показывать заметки", true);
        checkZametki.addActionListener(e -> perekluchitZametki());

        comboAnimatsii = new JComboBox<>(new String[]{
                "Без анимации", "Плавное появление", "Слева", "Справа", "Приближение", "Вращение"
        });

        sliderSkorost = new JSlider(1, 10, 5);
        sliderSkorost.setMajorTickSpacing(1);
        sliderSkorost.setPaintTicks(true);
        sliderSkorost.setPaintLabels(true);

        panelNastroiki.add(new JLabel("Анимация:"));
        panelNastroiki.add(comboAnimatsii);
        panelNastroiki.add(new JLabel("Скорость:"));
        panelNastroiki.add(sliderSkorost);
        panelNastroiki.add(checkZametki);

        // Меню
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFail = new JMenu("Файл");

        JMenuItem itemOtkrit = new JMenuItem("Открыть проект");
        itemOtkrit.addActionListener(e -> otkritProekt());

        JMenuItem itemSohranit = new JMenuItem("Сохранить проект");
        itemSohranit.addActionListener(e -> sohranitProekt());

        JMenuItem itemVihod = new JMenuItem("Выход");
        itemVihod.addActionListener(e -> System.exit(0));

        menuFail.add(itemOtkrit);
        menuFail.add(itemSohranit);
        menuFail.addSeparator();
        menuFail.add(itemVihod);

        JMenu menuRedakt = new JMenu("Редактировать");
        JMenuItem itemPoryadok = new JMenuItem("Изменить порядок слайдов");
        itemPoryadok.addActionListener(e -> izmenitPoryadok());
        menuRedakt.add(itemPoryadok);

        menuBar.add(menuFail);
        menuBar.add(menuRedakt);
        setJMenuBar(menuBar);

        // Сборка интерфейса
        mainPanel.add(panelUpravlenie, BorderLayout.NORTH);
        mainPanel.add(panelSlaida, BorderLayout.CENTER);
        mainPanel.add(panelZametka, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(panelNavigatsiya, BorderLayout.SOUTH);
        add(panelNastroiki, BorderLayout.EAST);
    }

    private void zagruzitIzobrazheniya() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] faili = fileChooser.getSelectedFiles();

            for (File fail : faili) {
                if (utils.Validation.proveritFail(fail)) {
                    Slaid slaid = fabrika.sozdatSlaid(fail);
                    kollektsiya.dobavit(slaid);
                }
            }

            if (kollektsiya.razmer() > 0) {
                sostoyanie = new ViewState.Builder()
                        .setTekushiyIndex(0)
                        .setVsegoSlaidi(kollektsiya.razmer())
                        .build();

                obnovitSlaid();
            }
        }
    }

    private void obnovitSlaid() {
        if (kollektsiya.pusto()) {
            labelIzobrazhenie.setText("Нет слайдов");
            textAreaZametka.setText("");
            return;
        }

        Slaid tekushiy = kollektsiya.poluchit(sostoyanie.getTekushiyIndex());

        if (tekushiy instanceof IzobrazhenieSlaid) {
            IzobrazhenieSlaid izobrazhenieSlaid = (IzobrazhenieSlaid) tekushiy;
            izobrazhenieSlaid.otobrazhit();

            ImageIcon icon = new ImageIcon(izobrazhenieSlaid.poluchitBuferIzobrazheniya());
            labelIzobrazhenie.setIcon(icon);
            labelIzobrazhenie.setText("");
        }

        textAreaZametka.setText(tekushiy.poluchitZametku());
        labelProgress.setText(sostoyanie.getProgressText());

        obnovitNavigatsiyu();
    }

    private void obnovitNavigatsiyu() {
        btnPred.setEnabled(!sostoyanie.isPerviySlaid());
        btnPerviy.setEnabled(!sostoyanie.isPerviySlaid());
        btnSled.setEnabled(!sostoyanie.isPosledniySlaid());
        btnPosledniy.setEnabled(!sostoyanie.isPosledniySlaid());
    }

    private void predidushiySlaid() {
        if (sostoyanie.getTekushiyIndex() > 0) {
            sostoyanie = new ViewState.Builder()
                    .setTekushiyIndex(sostoyanie.getTekushiyIndex() - 1)
                    .setVsegoSlaidi(kollektsiya.razmer())
                    .build();
            obnovitSlaid();
        }
    }

    private void sleduyushiySlaid() {
        if (sostoyanie.getTekushiyIndex() < kollektsiya.razmer() - 1) {
            sostoyanie = new ViewState.Builder()
                    .setTekushiyIndex(sostoyanie.getTekushiyIndex() + 1)
                    .setVsegoSlaidi(kollektsiya.razmer())
                    .build();
            obnovitSlaid();
        }
    }

    private void perekluchitSlaid(int index) {
        if (index >= 0 && index < kollektsiya.razmer()) {
            sostoyanie = new ViewState.Builder()
                    .setTekushiyIndex(index)
                    .setVsegoSlaidi(kollektsiya.razmer())
                    .build();
            obnovitSlaid();
        }
    }

    private void dobavitTekst() {
        if (kollektsiya.pusto()) {
            JOptionPane.showMessageDialog(this, "Сначала загрузите слайд");
            return;
        }

        String tekst = JOptionPane.showInputDialog(this, "Введите текст:");
        if (tekst != null && !tekst.trim().isEmpty()) {
            // Здесь будет добавление текста к текущему слайду
            JOptionPane.showMessageDialog(this, "Текст добавлен: " + tekst);
        }
    }

    private void dobavitSmailik() {
        if (kollektsiya.pusto()) {
            JOptionPane.showMessageDialog(this, "Сначала загрузите слайд");
            return;
        }

        String[] smailiki = {"😊 Веселый", "😢 Грустный", "😮 Удивленный",
                "😠 Сердитый", "😉 Подмигивающий"};
        String vibor = (String) JOptionPane.showInputDialog(this,
                "Выберите смайлик:", "Добавить смайлик",
                JOptionPane.PLAIN_MESSAGE, null, smailiki, smailiki[0]);

        if (vibor != null) {
            JOptionPane.showMessageDialog(this, "Смайлик добавлен: " + vibor);
        }
    }

    private void sohranitSlaid() {
        if (kollektsiya.pusto()) {
            JOptionPane.showMessageDialog(this, "Нет слайдов для сохранения");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("slaid.png"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File fail = fileChooser.getSelectedFile();
            try {
                // Здесь будет сохранение слайда
                JOptionPane.showMessageDialog(this,
                        "Слайд сохранен в: " + fail.getAbsolutePath());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Ошибка сохранения: " + e.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void perekluchitZametki() {
        boolean pokazivat = checkZametki.isSelected();
        textAreaZametka.setVisible(pokazivat);
        // Здесь обновление состояния
    }

    private void izmenitPoryadok() {
        if (kollektsiya.razmer() < 2) {
            JOptionPane.showMessageDialog(this, "Нужно хотя бы 2 слайда");
            return;
        }

        new RedaktorPoryadka(this, kollektsiya).setVisible(true);
        obnovitSlaid();
    }

    private void otkritProekt() {
        // Реализация открытия проекта
        JOptionPane.showMessageDialog(this, "Функция в разработке");
    }

    private void sohranitProekt() {
        // Реализация сохранения проекта
        JOptionPane.showMessageDialog(this, "Функция в разработке");
    }

    private void obnovitInterfeis() {
        obnovitSlaid();
        obnovitNavigatsiyu();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GlavnoeOkno okno = new GlavnoeOkno();
            okno.setVisible(true);
        });
    }
}