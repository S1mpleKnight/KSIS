package by.bsuir.ksis.third.server.service;

import java.io.IOException;

public interface TransferDataService {

    /**
     * Добавление в конец файла
     * @param pathToFile - путь к файлу
     * @param data - новые данные
     */
    void addToTheFilesEnd(String pathToFile, byte[] data) throws IOException;

    /**
     * Чтение файла
     * @param pathToFile - путь к файлу
     * @return данные из файла
     */
    byte[] read(String pathToFile) throws IOException;

    /**
     * Перезапись файла
     * @param pathToFile - путь к файлу, который подлежит изменениям
     * @param data - новые данные
     */
    void update(String pathToFile, byte[] data) throws IOException;

    /**
     * Удаление файла
     * @param pathToFile - путь к файлу, который нужно удалить
     */
    void delete(String pathToFile) throws IOException;

    /**
     * @param source - путь к файлу, который копируем
     * @param destination - путь полученного в результате копирования файла
     */
    void copy(String source, String destination) throws IOException;
}
