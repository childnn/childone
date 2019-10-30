package org.anonymous.lombok.nonnull;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 16:26
 */
public class NonNullDemo {

    @Getter
    @Setter
    @NonNull
    private String name;

   /*
    编译:
    @NonNull
    public String getName() {
        return this.name;
    }

    public void setName(@NonNull String name) {
        if (name == null) {
            throw new NullPointerException("name is marked non-null but is null");
        } else {
            this.name = name;
        }
    }*/
}
