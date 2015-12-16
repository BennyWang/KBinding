package com.benny.library.neobinding.bind;

import rx.functions.Action1;

/**
 * Created by benny on 12/16/15.
 */

public interface Command {
     void execute(Action1<? super Boolean> canExecute);
}
