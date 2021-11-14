// import java.util.ArrayList;
// import java.util.List;
//
// /**
//  * ~~ Talk is cheap. Show me the code. ~~ :-)
//  *
//  * @author MiaoOne
//  * @since 2021/9/15 14:19
//  */
// public class JDK11 {
//
//     public static void main(String[] args) {
//         var x = 1;
//         List<Object> list = new ArrayList<>();
//         list.add(1);
//         list.add("");
//
//         // 13
//         /*var m = */switch (x) {
//             case 1, 2, 3 -> System.out.println(x);
//             case 4, 5 -> throw new IllegalArgumentException();
//             default -> System.out.println();
//         }
//
//         // 返回值
//         var m = switch (x) {
//             case 1, 3, 5 -> 5;
//             case 9, 8 -> 6;
//             default -> 1;
//         };
//         //
//         // // 15
//         // String html = """
//         //       <html>
//         //           <body>
//         //               <p>Hello, world</p>
//         //           </body>
//         //       </html>
//         //       """;
//         // if (html instanceof String a) {
//         //
//         // }
//     }
//
// }
