package com.tcs.edu.decorator;

/**
 * Класс для маппинга уровня важности сообщения и его постфикса
 *
 * @author a.v.demchenko
 */
public class SeverityMapper {

    /**
     * Метод предназначен для оперделения постфикса важности в зависимости от его уровне
     *
     * @param severity уровень важности
     * @return постфикс сообщения
     */
    static String severityMapper(Severity severity) {
        String severityString = null;
        switch (severity) {
            case MINOR:
                severityString = "(!)";
                break;
            case REGULAR:
                severityString = "(!!)";
                break;
            case MAJOR:
                severityString = "(!!!)";
                break;
            default:
                severityString = "()";
        }
        return severityString;
    }
}
