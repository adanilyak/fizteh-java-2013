package ru.fizteh.fivt.students.paulinMatavina.filemap;

import ru.fizteh.fivt.students.paulinMatavina.utils.*;

public class DbExit implements Command {
    @Override
    public int execute(String[] args, State state) {
        state.exitWithError(0);
        return 0;
    }
    
    @Override
    public String getName() {
        return "exit";
    }
    
    @Override
    public int getArgNum() {
        return 0;
    }   
    
    @Override
    public boolean spaceAllowed() {
        return false;
    }
}
