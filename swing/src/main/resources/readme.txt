JFC: Java Foundation Classes

java.awt: abstract window toolkit 抽象窗口工具包. 早期编写图形界面应用程序的包.
Swing: 为解决 AWT 存在的问题而新开发的图形界面包. Swing 是对 AWT 的改良和扩展.

awt 线程安全; swing 线程不安全
AWT 组件是 “线程安全的(thread-safe)”,这意味着我们不需要关心在应用程序中是哪一个线程对 GUI 进行了更新。
    这个特性可以减少很多 GUI 更新的问题,不过使 AWT GUI 运行的速度更慢了。

AWT 让我们可以以自顶向下(top-down) 或自底向上(bottom-up) 或以任意组合顺序来构建 GUI。
    自顶向下的意思是在创建子组件之前首先创建容器组件；自底向上的意思是在创建容器(或父)组件之前创建子组件。
    在后一种情况中,组件的存在并不依赖于父容器,其父容器可以随时改变。

AWT 的图形函数与操作系统提供的图形函数有着一一对应的关系。
   也就是说,当我们利用 AWT构件图形用户界面的时候,实际上是在利用操作系统的图形库。
   不同的操作系统其图形库的功能可能不一样,在一个平台上存在的功能在另外一个平台上则可能不存在。
   为了实现Java语言所宣称的"一次编译,到处运行"的概念,AWT不得不通过牺牲功能来实现平台无关性。
   因此,AWT 的图形功能是各操作系统图形功能的“交集”。
   因为AWT是依靠本地方法来实现功能的,所以AWT控件称为“重量级控件”。
而 Swing ,不仅提供了AWT 的所有功能,还用纯粹的 Java 代码对 AWT 的功能进行了大幅度的扩充。
       例如：并不是所有的操作系统都提供了对树形控件的支持, Swing则利用了AWT中所提供的基本作图方法模拟了一个树形控件。
       由于 Swing是用纯粹的Java代码来实现的,因此Swing控件在各平台通用。
       因为Swing不使用本地方法,故Swing控件称为“轻量级控件”。

       AWT和Swing之间的区别：
       1) AWT 是基于本地方法的C/C++程序,其运行速度比较快；Swing 是基于 AWT 的 Java 程序,其运行速度比较慢。
       2) AWT 的控件在不同的平台可能表现不同,而 Swing 在所有平台表现一致。
SWT: Standard Windows Toolkit with JFace
    使用 SWT 只能自顶向下地构建 GUI。因此，如果没有父容器，子控件也就不存在了；通常父容器都不能在以后任意改变。
    这种方法不如 AWT/Swing 灵活。控件是在创建时被添加到父容器中的，在销毁时被从父容器中删除的。
    而且 SWT 对于 style 位的使用只会在构建时进行，这限制了有些 GUI 控件的灵活性。
    有些风格只是一些提示性的，它们在所有平台上的行为可能并不完全相同。
    与 Swing 类似，SWT 组件也不是线程安全的，这意味着您必须要关心在应用程序中是哪个线程对 GUI 进行了更新。
    如果在使用线程时发生了错误，就会抛出异常。

-------------------------------------------------------------------------------------------------------------------------
基本的 AWT Class 树(全部在 java.awt 包中, “*” 表示抽象)
Object
    CheckboxGroup
    *Component
        Button
        Canvas
        Checkbox
        Choice
        Container
            Panel
                Applet
            ScrollPane
            Window
                Dialog
                Frame
        Label
        List
        TextComponent
            TextArea
            TextField
    MenuComponent
        MenuBar
        MenuItem
            CheckboxMenuItem
            Menu
                PopupMenu
-----------------------------------------------
AWT 提供了下面的布局管理器(全部在 java.awt 包中,“*” 表示接口)
*LayoutManager
    FlowLayout
    GridLayout
    *LayoutManager2
        BorderLayout
        CardLayout
        GridBagLayout
---------------------------------------------------------------
AWT 提供了以下事件(大部分在 java.awt.events 包中)
Object
    EventObject
        AWTEvent
            ActionEvent
            AdjustmentEvent
            ComponentEvent
                ContainerEvent
                FocusEvent
                InputEvent
                    KeyEvent
                    MouseEvent
                        MouseWheelEvent
                PaintEvent
                WindowEvent
            HierarchyEvent
            InputMethodEvent
            InvocationEvent
            ItemEvent
            TextEvent


--------------------------------------------------------------------------------------------------------------------------------
基本的 Swing Class 树(全部在 javax.swing 包或其子包中,“*” 表示抽象类)
Object
    *Component
        Container
            *JComponent
                *AbstractButton
                    JButton
                    JMenuItem
                        JCheckBonMenuItem
                        JMenu
                        JRadioButtonMenuItem
                    *JToggleButton
                        JCheckBox
                        JRadioButton
                Box
                Filler
                JColorChooser
                JComboBox
                JDesktopIcon
                JFileChooser
                JInternalFrame
                JLabel
                JLayeredPane
                    JDesktopPane
                JList
                JMenuBar
                JOptionPane
                JPanel
                JPopupMenu
                JProgressBar
                JRootPane
                JScrollBar
                JScrollPane
                JSeparator
                JSlider
                JSplitPane
                JTabbedPane
                JTable
                JTableHeader
                *JTextComponent
                    JEditorPane
                        FrameEditorPane
                        JTextPane
                    JTextArea
                    JTextField
                        JPasswordField
                JToolBar
                JToolTip
                JTree
                JViewport
                    ScrollableTabViewport
            Panel
                Applet
                    JApplet
            Window
                Dialog
                    JDialog
                Frame
                    JFrame
                JWindow
--------------------------------------------------------
Swing 提供了以下 LayoutManagers(全部在 javax.swing 包或其子包中,“*” 表示接口)
*LayoutManager
    CenterLayout
    *LayoutManager2
        BoxLayout
        OverlayLayout
        SpringLayout
---------------------------------------------------------
Swing 提供了以下事件(大部分在 javax.swing.events 包及其子包中)
Object
    EventObject
        AWTEvent
            AncestorEvent
            ComponentEvent
                InputEvent
                    KeyEvent
                        MenuKeyEvent
                    MouseEvent
                        MenuDragMouseEvent
            InternalFrameEvent


-----------------------------------------------------------------------------------------------------------------------------------------
基本的 SWT Class 树(大部分在 org.ecipse.swt.widgets 或 org.eclipse.swt.custom 包或子包中,“*” 表示抽象类,“!” 表示在 custom 包中,“~” 表示在其他包中)
Object
    *Dialog
         ColorDialog
         DirectoryDialog
         FileDialog
         FontDialog
         MessageDialog
         PrintDialog
    *Widget
        Menu
        *Item
            CoolItem
            !CTabItem
            MenuItem
            TabItem
            TableColumn
            TableItem
            TableTreeItem
            ToolItem
            TrayItem
            TreeColumn
            TreeItem
        *Control
            Button
            Label
            ProgressBar
            Sash
            Scale
            Scrollable
                Composite
                    ~Browser
                    Canvas
                        *~AbstractHyperlink
                            ~Hyperlink
                                ~ImageHyperlink
                            *~ToggleHyperline
                                ~TreeNode
                                ~Twistie
                        AnimatedProgress
                        !CLabel
                        Decorations
                            Shell
                        FormText
                        StyledText
                        TableCursor
                    !CBanner
                    !CCombo
                    Combo
                    CoolBar
                    !CTabFolder
                    ~ExpandableComposite
                        ~Section
                    ~FilteredList
                    ~FilteredTree
                    ~Form
                    Group
                    ~PageBook
                    ProgressIndicator
                    !SashForm
                    !ScrolledComposite
                    TabFolder
                    Table
                    TableTree
                    ToolBar
                    Tray
                    Tree
                    ViewForm
                List
                Text
            Slider
-------------------------------------------------------------
SWT 提供了以下布局管理器(大部分在 org.eclipse.swt.layout 和 org.eclipse.swt.custom 包或子包中,“*” 表示接口,“!” 表示在 custom 包中)
*Layout
    FillLayout
    FormLayout
    GridLayout
    RowLayout
    !StackLayout
----------------------------------------------------------------
SWT 提供了以下事件(大部分在 org.eclipse.swt.events 包或 org.eclipse.swt.custom 包或其子包中,“*” 表示抽象,“!” 表示在 custom 包中)
Object
    EventObject
        SWTEventObject
            TypedEvent
                AimEvent
                !BidiSegmentEvent
                ControlEvent
                !CTabFlolderEvent
                DisposeEvent
                DragSourceEvent
                DragTargetEvent
                !ExtendedModifyEvent
                focusEvent
                HelpEvent
                KeyEvent
                    TraverseEvent
                    VerifyEvent
                !LineBackgroundEvent
                !LineStyleEvent
                MenuEvent
                ModifyEvent
                MouseEvent
                PaintEvent
                SelectionEvent
                    TreeEvent
                ShellEvent
                !TextChangedEvent
                !TextChangingEvent
-------------------------------------------------------------------------------------------
Swing
容器:
    在 Swing 中, 任何其他组件都必须位于一个顶层容器中. JFrame 窗口和 JPanel 面板是常用的顶层容器.
    当创建一个 JFrame 类的实例化对象后, 其他组件并不能够直接放到容器上面, 需要将组件添加至内容窗格, 而不是
    直接添加至 JFrame 对象. eg-code: frame.getContentPane().add(b);
    JPanel 是一个中间容器, 他能容纳组件并将组件组合在一起, 但他本身必须添加到其他容器中使用.
布局管理器:
    在使用 Swing 向容器添加组件时，需要考虑组件的位置和大小。
    如果不使用布局管理器，则需要先在纸上画好各个组件的位置并计算组件间的距离，再向容器中添加。
    这样虽然能够灵活控制组件的位置，实现却非常麻烦。
    为了加快开发速度，Java 提供了一些布局管理器，它们可以将组件进行统一管理，
    这样开发人员就不需要考虑组件是否会重叠等问题。所有布局都实现 LayoutManager 接口。
    1. BorderLayout
        BorderLayout(边框布局管理器)是 Window、JFrame 和 JDialog 的默认布局管理器。
        边框布局管理器将窗口分为 5 个区域：North、South、East、West 和 Center。
        边框布局管理器并不要求所有区域都必须有组件，如果四周的区域(North、South、East 和 West 区域)
        没有组件，则由 Center 区域去补充。如果单个区域中添加的不只一个组件，
        那么后来添加的组件将覆盖原来的组件，所以，区域中只显示最后添加的一个组件。
    2. FlowLayout
        FlowLayout(流式布局管理器)是 JPanel 和 JApplet 的默认布局管理器。
        FlowLayout 会将组件按照从上到下、从左到右的放置规律逐行进行定位。
        与其他布局管理器不同的是，FlowLayout 布局管理器不限制它所管理组件的大小，
        而是允许它们有自己的最佳大小。
    3. CardLayout
        CardLayout 布局管理器将容器分成许多层，每层的显示空间占据整个容器的大小，
        但是每层只允许放置一个组件.
    4. GridLayout
        GridLayout(网格布局管理器)为组件的放置位置提供了更大的灵活性。
        它将区域分割成行数(rows)和列数(columns)的网格状布局，组件按照由左至右、由上而下的次序排列填充到各个单元格中。
        GridLayout 布局管理器总是忽略组件的最佳大小，而是根据提供的行和列进行平分。该布局管理的所有单元格的宽度和高度都是一样的。
    5. GridBagLayout
        GridBagLayout(网格包布局管理器)是在网格基础上提供复杂的布局，是最灵活、 最复杂的布局管理器。
        GridBagLayout 不需要组件的尺寸一致，允许组件扩展到多行多列。每个 GridBagLayout 对象
        都维护了一组动态的矩形网格单元，每个组件占一个或多个单元，所占有的网格单元称为组件的显示区域。
        GridBagLayout 所管理的每个组件都与一个 GridBagConstraints 约束类的对象相关。
        这个约束类对象指定了组件的显示区域在网格中的位置，以及在其显示区域中应该如何摆放组件。
        除了组件的约束对象，GridBagLayout 还要考虑每个组件的最小和首选尺寸，以确定组件的大小。
        --
        1. gridx 和 gridy
            用来指定组件左上角在网格中的行和列。容器中最左边列的 gridx 为 0，最上边行的 gridy 为 0。
            这两个变量的默认值是 GridBagConstraints.RELATIVE，表示对应的组件将放在前一个组件的右边或下面。
        2. gridwidth 和 gridheight
            用来指定组件显示区域所占的列数和行数，以网格单元而不是像素为单位，默认值为 1。
        3. fill
            指定组件填充网格的方式，可以是如下值：GridBagConstraints.NONE(默认值)、
            GridBagConstraints.HORIZONTAL(组件横向充满显示区域，但是不改变组件高度)、
            GridBagConstraints.VERTICAL(组件纵向充满显示区域，但是不改变组件宽度)
            以及 GridBagConstraints.BOTH(组件横向、纵向充满其显示区域)。
        4. ipadx 和 ipady
            指定组件显示区域的内部填充，即在组件最小尺寸之外需要附加的像素数，默认值为 0。
        5. insets
            指定组件显示区域的外部填充，即组件与其显示区域边缘之间的空间，默认组件没有外部填充。
        6. anchor
            指定组件在显示区域中的摆放位置。可选值有 GridBagConstraints.CENTER(默认值)、
            GridBagConstraints.NORTH、GridBagConstraints.NORTHEAST、GridBagConstraints.EAST、
            GridBagConstraints.SOUTH、GridBagConstraints.SOUTHEAST、GridBagConstraints.WEST、
            GridBagConstraints.SOUTHWEST 以及 GridBagConstraints.NORTHWEST。
        7. weightx 和 weighty
            用来指定在容器大小改变时，增加或减少的空间如何在组件间分配，默认值为 0，
            即所有的组件将聚拢在容器的中心，多余的空间将放在容器边缘与网格单元之间。
            weightx 和 weighty 的取值一般在 0.0 与 1.0 之间，数值大表明组件所在的行或者列将获得更多的空间。
    6. BoxLayout
        BoxLayout(盒布局管理器)通常和 Box 容器联合使用，Box 类有以下两个静态方法。
          createHorizontalBox()：返回一个 Box 对象，它采用水平 BoxLayout，即 BoxLayout 沿着水平方向放置组件，让组件在容器内从左到右排列。
          createVerticalBox()：返回一个 Box 对象，它采用垂直 BoxLayout，即 BoxLayout 沿着垂直方向放置组件，让组件在容器内从上到下进行排列。
        使用盒式布局可以像使用流式布局一样简单地将组件安排在一个容器内。包含盒式布局的容器可以嵌套使用，
        最终达到类似于无序网格布局那样的效果，却不像使用无序网格布局那样麻烦。
标签: JLabel
按钮: JButton
文本:
    单行文本框: JTextField
    文本域: JTextArea
选择:
    复选框: JCheckBox
    单选框: JRadioButton
            单选按钮与复选框类似都有两种状态，不同的是一组单选按钮中只能有一个处于选中状态。
            Swing 中 JRadioButton 类实现单选按钮，它与 JCheckBox 一样都是从 JToggleButton 类派生出来的。
            JRadioButton 通常位于一个 ButtonGroup 按钮组中，不在按钮组中的 JRadioButton 也就失去了单选按钮的意义。
            在同一个 ButtonGroup 按钮组中的单选按钮，只能有一个单选按钮被选中。
            因此，如果创建的多个单选按钮其初始状态都是选中状态，则最先加入 ButtonGroup 按钮组的单选按钮
            的选中状态被保留，其后加入到 ButtonGroup 按钮组中的其他单选按钮的选中状态被取消。
    下拉框: JComboBox
    列表框: JList
---------------------------------------------------------------------------------------------------------------------
事件处理
    在事件处理的过程中，主要涉及三类对象。
    Event(事件)：用户对组件的一次操作称为一个事件，以类的形式出现。例如，键盘操作对应的事件类是 KeyEvent。
    Event Source(事件源)：事件发生的场所，通常就是各个组件，例如按钮 Button。
    Event Handler(事件处理者)：接收事件对象并对其进行处理的对象事件处理器，通常就是某个 Java 类中负责处理事件的成员方法。
    由于同一个事件源上可能发生多种事件，因此，Java 采取了授权模型（Delegation Model），
    事件源可以把在其自身上所有可能发生的事件分别授权给不同的事件处理者来处理。
    例如，在 Panel 对象上既可能发生鼠标事件，也可能发生键盘事件，该 Panel 对象可以授权给事件处理者 a 来处理鼠标事件，
    同时授权给事件处理者 b 来处理键盘事件。
    有时也将事件处理者称为监听器，主要原因在于监听器时刻监听事件源上所有发生的事件类型，
    一旦该事件类型与自己所负责处理的事件类型一致，就马上进行处理。授权模型把事件的处理委托给外部的处理实体进行处理，
    实现了将事件源和监听器分开的机制。
    事件处理者（监听器）通常是一个类，该类如果能够处理某种类型的事件，就必须实现与该事件类型相对的接口。
    例如，一个 ButtonHandler 类之所以能够处理 ActionEvent 事件，原因在于它实现了与 ActionEvent 事件
    对应的接口 ActionListener。每个事件类都有一个与之相对应的接口。
 动作事件监听器
     动作事件监听器是 Swing 中比较常用的事件监听器，很多组件的动作都会使用它监听，像按钮被里击、列表框中选择一项等。
     与动作事件监听器有关的信息如下。
     事件名称：ActionEvent。
     事件监听接口: ActionListener。
     事件相关方法：addActionListener() 添加监听，removeActionListener() 删除监听。
     涉及事件源：JButton、JList、JTextField 等。
 焦点事件监听器
     除了单击事件外，焦点事件监听器在实际项目中应用也比较广泛，
     例如将光标离开文本框时弹出对话框，或者将焦点返回给文本框等。
     与焦点事件监听器有关的信息如下。
     事件名称：FocusEvent。
     事件监听接口： FocusListener。
     事件相关方法：addFocusListener() 添加监听，removeFocusListener() 删除监听。
     涉及事件源：Component 以及派生类。
     FocusEvent 接口定义了两个方法，分别为 focusGained() 方法和 focusLost() 方法，
     其中 focusGained() 方法是在组件获得焦点时执行，focusLost() 方法是在组件失去焦点时执行。
 列表项选择监听器
      ListSelectionListener
---------------------------------------------------------------------------------------------------------------------
布局组件:
    滑块: JSlider 滑块（JSlider）是一个允许用户在有限区间内通过移动滑块来选择值的组件。
    进度条: 进度条（JProgressBar）是一种以可视化形式显示某些任务进度的组件。
        JProgressBar 类实现了一个用于为长时间的操作提供可视化指示器的 GUI 进度条。
        在任务的完成进度中，进度条显示该任务完成的百分比。
        此百分比通常由一个矩形以可视化形式表示，该矩形开始是空的，
        随着任务的完成逐渐被填充。此外，进度条可显示此百分比的文本表示形式。
        如果要执行一个未知长度的任务，可以调用 setIndeterminate(true) 将进度条设置为不确定模式。
        不确定模式的进度条将持续地显示动画来表示正进行的操作。
        一旦可以确定任务长度和进度量，则应该更新进度条的值，将其切换到确定模式。
    计时器: 计时器（Timer）组件可以在指定时间间隔触发一个或多个 ActionEvent。
        设置计时器的过程包括创建一个 Timer 对象，在该对象上注册一个或多个动作侦听器，
        以及使用 start() 方法启动该计时器。
    菜单: 菜单由 Swing 中的 JMenu 类实现，可以包含多个菜单项和带分隔符的菜单。
        在菜单中，菜单项由 JMenuItem 类表示，分隔符由 JSeparator 类表示。
        菜单本质上是带有关联 JPopupMenu 的按钮。当按下“按钮”时，就会显示 JPopupMenu。
        如果“按钮”位于 JMenuBar 上，则该菜单为顶层窗口。
        如果“按钮”是另一个菜单项，则 JPopupMenu 就是“下拉”菜单。
        弹出式菜单: JPopupMenu
        弹出式菜单由 JPopupMenu 类实现，它是一个可弹出并显示一系列选项的小窗口。
        它还用于当用户选择菜单项并激活它时显示的“右拉式(pull-right)”菜单，
        可以在想让菜单显示的任何其他位置使用。
    工具栏: JToolBar
    选择器:
        文件选择器: JFileChooser. 文件选择器为用户能够操作系统文件提供了桥梁.
        颜色选择器: JColorChooser.
    对话框:
        对话框通常用作从用户处接收附加信息，或者提供发生了某种事件的通知。
        Java 提供了 JOptionPane 类，用来创建标准对话框，也可以通过扩展 JDialog 类
        创建自定义的对话框。JOptionPane 类可以用来创建 4 种类型的标准对话框：
        确认对话框、消息对话框、输入对话框和选项对话框。
    表格:
    树:
       如果要显示一个层次关系分明的一组数据，用树结构是最合适的。树如同 Windows 资源管理器的左半部，可通过单击文件夹展开或者收缩内容。
       Swing 使用 JTree 类实现树，它的主要功能是把数据按照树状进行显示，其数据来源于其他对象。
       JTree 树中最基本的对象叫作节点，表示在给定层次结构中的数据项。树以垂直方式显示数据，每行显示一个节点。
       树中只有一个根节点，所有其他节点从这里引出。除根节点外，其他节点分为两类：一类是代子节点的分支节点，另一类是不带子节点的叶节点。
    选项卡:
        使用选项卡可以在有限的布局空间内展示更多的内容。
        JTabbedPane 类创建的选项卡可以通过单击标题或者图标在选项卡之间进行切换。
    文本编辑器:



