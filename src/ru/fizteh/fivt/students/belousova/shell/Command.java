package ru.fizteh.fivt.students.belousova.shell;

import java.io.IOException;

public interface Command {
    String getName();

    void execute(String[] args) throws IOException;

    int getArgCount();
}
