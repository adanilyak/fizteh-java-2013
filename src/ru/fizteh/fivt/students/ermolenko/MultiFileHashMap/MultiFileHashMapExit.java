package ru.fizteh.fivt.students.ermolenko.multifilehashmap;

import ru.fizteh.fivt.students.ermolenko.shell.Command;

import java.io.IOException;

public class MultiFileHashMapExit implements Command<MultiFileHashMapState> {

    @Override
    public String getName() {

        return "exit";
    }

    @Override
    public void executeCmd(MultiFileHashMapState inState, String[] args) throws IOException {

        if (inState.getCurrentTable() != null) {
            /*
            File fileForWrite = ((MultiFileHashMapTable) inState.getCurrentTable()).getDataFile();
            Map<String, String> mapForWrite = ((MultiFileHashMapTable) inState.getCurrentTable()).getDataBase();
            MultiFileHashMapUtils.write(fileForWrite, mapForWrite);
            */
            inState.getCurrentTable().commit();
        }
        System.exit(0);
    }
}
