package org.anonymous.lombok.with;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.With;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 16:41
 */
@AllArgsConstructor
public class WithDemo {
    @With(AccessLevel.PROTECTED)
    @NonNull
    private final String name;
    @With
    private final int age;

   /* public WithDemo(@NonNull String name, int age) {
        this.name = name;
        this.age = age;
    }*/

    /*
    * 编译:
    *     public WithDemo(@NonNull String name, int age) {
                if (name == null) {
                    throw new NullPointerException("name is marked non-null but is null");
                } else {
                    this.name = name;
                    this.age = age;
                }
          }

        protected WithDemo withName(@NonNull String name) {
            if (name == null) {
                throw new NullPointerException("name is marked non-null but is null");
            } else {
                return this.name == name ? this : new WithDemo(name, this.age);
            }
        }

        public WithDemo withAge(int age) {
            return this.age == age ? this : new WithDemo(this.name, age);
        }
    * */

}
