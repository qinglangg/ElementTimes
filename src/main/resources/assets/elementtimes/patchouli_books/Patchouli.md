> 这是很久前我整理的 Patchouli mod 手册的写法
> 若有改动，请以[官方 Wiki](https://github.com/Vazkii/Patchouli/wiki/Getting-Started) 为准

# 约定

- json 文件中，使用 // 表示注释，实际使用时请手动删除
- json 文件中，注释中提到 必选 的是必须手动输入的属性，其他非字符串类如无特殊标明，则为默认值
- json 文件中，有表示字符串
  - \<xxx_id> 表示资源 id 值，比如 mod_id，entry_id 等。原版 entity_id 可以从 EntityList 类中找到，其他 id 在各自对应类中
  - \<string> 表示任意普通字符串
  - \<formatted> 表示可格式化字符串，详见 其他相关格式/Text Formatting
  - \<flag> 表示 flag，详见 其他相关格式/ConfigFlags
  - \<color> 表示颜色，如 "AABBDD", "ABD"(等同于 "0A0B0D")
  - \<item> 表示 ItemStack，详见 其他相关格式/ItemStack String
  - \<resource> 表示 图片资源格式，如 minecraft:textures/gui/toasts.png
  - \<advancement> 表示一个进度/成就，当该进度达成时可以解锁
    - 整合包，可使用 Triumph mod 自定义成就
    - Mod，放入 /assets/\<mod_id>/advancements
  - \<variable> 表示可以传入一个参数，使用 #var
    - 所有参数都可以使用函数再次处理 #var->\<function> 这种格式
  - \<function> 表示一个函数，详见 其他相关格式/Function
  - "<>|<>|<>..." 接收多种格式

# Patchouli

- Mod
  - API（可选）
  - /assets/\<mod_id>/patchouli_books
- 整合包
  - mod
  - .minecraft/patchouli_books

# patchouli_books 结构

- \<book_id>

  > 书名。一个包中可以有多本书，以 \<your_mod_id>.\<your_book_id> 或 patchouli:\<your_book_id> 形式注册

  - book.json

    > 书籍信息，详见 Book Json

  - en_us

    > 对应语言的内容版本。en_us 作为主要（默认）内容加载，因此 en_us 应总是存在

    - entries

      > 章节，可包含子目录
      >
      > id: 根据其子目录获取，如：
      >
      >   /en_us/entries/misc/entry.json ==> entry_id 为 "misc/entry"

    - categories

      > 类别，可包含子目录
      >
      > 一个类别没有章节时会显示锁定
      >
      > id: 根据其子目录获取，如：
      >
      >   /en_us/categories/misc/category.json ==> category_id 为 "misc/category"

    - templates

      > 样板
      >
      > id: 根据其子目录获取，如：
      >
      > /en_us/templates/misc/template.json ==> category_id 为 "misc/template"

# 基本文件格式

## Book Json

```json
{
    // 必选，书籍名
    // 当作为 mod api 时可以使用 localization 值用以本地化
    "name": "<string>",
    // 必选，格式化字符串，将显示在引导页中，即翻开左侧第一页
    // 当作为 mod api 时可以使用 localization 值用以本地化
    "landing_text": "<formatted>",
    // 字符串数组 用于检测数组内 mod 的进度/成就完成情况，用以解锁
    // 不允许添加 "minecraft"
    "advancement_namespaces": ["<mod_id>"],
    // 背景纹理 内置 patchouli:textures/gui/book_{blue/brown/cyan/gray/green/purple/red}}.png
    "book_texture": "<resource>: patchouli:textures/gui/book_brown.png",
    // 填充纹理 用于替换奇数页空白处的立方体
    "filler_texture": "<resource>",
    // 封面
    "crafting_texture": "<resource>",
    // 模型 默认提供 patchouli:book_{blue/brown/cyan/gray/green/purple/red}
    // model 中添加 completion 属性，可定义达到某一进度后解锁内容 （存疑）
    // 我改成 red 显示贴图错误
    "model": "<model_id>:  patchouli:book_brown",
    // 文本颜色 <color>
    "text_color": "000000",
    // 标题文本颜色 <color>
    "header_color": "333333",
    // 引导页中书名颜色 <color>
    "nameplate_color": "FFDD00",
    // 链接颜色 <color>
    "link_color": "0000EE",
    // 链接悬停颜色 <color>
    "link_hover_color": "8800EE",
    // 进度条颜色 <color>
    "progress_bar_color": "FFFF55",
    // 进度条背景颜色 <color>
    "progress_bar_background": "",
    // 打开书本时播放音效 音效资源
    "open_sound": "<sound_id>",
    // 翻阅书本时播放音效 音效资源
    "flip_sound": "<sound_id>",
    // 图书总目录显示的图标
    "index_icon": "<resource>|<item>",
    // 是否启用进度条
    "show_progress": true,
    // 版本 显示为 “第 <version> 版”
    "version": 0,
    // 副标题。当 version 为 0 或 空 时，副标题内容显示在引导页书名下方
    "subtitle": "<formatted>",
    // 书籍所在创造模式标签，标签名
    // minecraft 包含 {buildingBlocks/decorations/redstone/transportation/food/tools/combat/brewing}
    "creative_tab": "misc",
    // 关联进度选项卡名称
    "advancements_tab": "<string>",
    // 是否禁止 Patchouli 创建对应书籍，需要自定义一个 Item （存疑）
    "dont_generate_book": false,
    // dont_generate_book 为 true 时，在此处传入自定义 Item 的 ItemStackString
    "custom_book_item": "<item>",
	// 当书籍有新条目可用时，是否显示 Toast 通知
    "show_toasts": true,
    // 宏，用于格式化书籍内容，详见 其他相关格式/宏
    "macros": {},
    // 设置此书为另一本书的扩展。该书内容将作为 <mod_id>:<book_id> 书籍的一部分
    // 设置此值后，忽略本书其他设置，继承 <mod_id>:<book_id> 的条目，类别，模板，宏
    "extend": "<mod_id>:<book_id>",
    // 是否允许别的书作为该书的扩展（即是否允许其他书的 extend 值设为此书的 id）
    "allow_extensions": true
}
```

## Category Json

```json
{
    // 必选，类别名
    "name": "<string>",
    // 必选，格式化字符串，类别描述，显示在类别页左页
    "description": "<formatted>",
    // 必选，png 结尾的图片资源 id，或 ItemStackString
    "icon": "<resource>|<item>",
    // 父类别（如果存在）类别 id
    "parent": "<category_id>",
    // ConfigFlags
    "flag": "<flag>",
    // 排序，从低到高
    "sortnum": 0,
    // true 时，当锁定时不显示
    "secret": false
}
```

## Entry

### 格式

```json
{
    // 必选 章节名
    "name": "<string>",
    // 必选 分类 id
    "category": "<category_id>",
    // 必选 png 结尾的图片资源 id，或 ItemStackString
    "icon": "<resource>|<item>",
    // 必选 页面内容，详见 基本文件格式/Entry/type
    "pages": [{}],
    // 解锁该章节所需进度
    "advancement": "<advancement>",
    // ConfigFlags
    "flag": "<flag>",
    // 优先级。true 时，显示为斜体，置顶
    "priority": false,
    // true 时，解锁前不显示，不计入完成百分比，解锁是提示为附加行
    "secret": false,
    // true 时，不显示未读标记(!!)
    "read_by_default": false,
    // 排序，由小到大
    "sortnum": 0,
    // 玩家完成该章节所需进度。该进度将置顶显示，并在图标旁显示 ? 图标
    "turnin":"<advancement>"
}
```



### type

#### 通用属性

> Template 共有参数

```json
{
    // 必填 组件类型
    "type": "<string>",
    // 当该进度未完成时，锁定该组件
    "advancement": "<advancement>",
    // 根据 flag 决定是否显示该组件
    "flag": "<flag>",
    // 用于 Text Formatting
    "anchor": "<string>"
}
```

#### text

> text 类型
>
> entry#pages[0] 应总是 text，此时该 text page 将显示在第一页并在顶端显示 entry#name

```json
{
    // 必选 该 page 显示的文字 格式化字符串
    "text": "<formatted>",
    // 如果该 page 为 pages 第一项，则无效；
    // 非第一项，则会显示在顶端居中，text 属性内容则在其下方显示
    "title": "<string>",
    // 字体颜色。默认为 book.json 的 text_color 属性
    "color": "000000",
    // 文本最大宽度，默认为该页面的宽度
    "max_width": 0,
    // 行高
    "line_height": 9
}
```



#### item

>展示一个物品。鼠标悬停可以查看工具提示

```json
{
    
    "item": "<item>|<string>",
    "framed": false,
    "link_recipe": false
}
```



#### image

> 显示一组图片

```json
{
    // 必选 要显示的图片资源数组
    // 资源必须为 png 格式，理想尺寸 256*256，图片内容集中在左上角 200*200 区域，占据书页整体 1/2
    "images": ["<resource>"],
    // 标题，显示在图片上方
    "title": "<string>",
    // 是否显示图片边界
    // 遍布整个画布的图像，应设置为 true
    "border": false,
    // 格式化字符串，显示在图片下方
    "text": "<formatted>"
}
```



#### crafting

> 显示1-2个合成配方

```json
{
    // 必选 第一个合成配方 id
    "recipe": "<recipe_id>",
    // 第二个合成配方 id
    "recipe2": "<recipe_id>",
    // 显示于页面顶端。设有标题时，隐藏配方输出名称
    "title": "<string>",
    // 格式化字符串 显示于配方下方。若有第二个配方，则隐藏该项
    "text": "<formatted>"
}
```



#### smelting

> 显示1-2个熔炉配方

```json
{
    // 必选 第一个熔炉配方，输入原料的 ItemStackString
    "recipe": "<item>",
    // 第二个熔炉配方，输入原料的 ItemStackString
    "recipe2": "<item>",
    // 显示于页面顶端。设有标题时，隐藏配方输出名称
    "title": "<string>",
    // 格式化字符串 显示于配方下方
    "text": "<formatted>"
}
```



#### multiblock

> 多方块结构
>
> multiblock_id 与 multiblock 必须二选一

```json
{
    // 必选 多方块结构名称
    "name": "<string>",
    // 仅 mod，需要注册
    "multiblock_id": "<multiblock_id>",
    // 多方块结构
    "multiblock": {},
    // 是否显示 可视化 按钮
    "enable_visualize": true,
    // 格式化字符串，显示在多方块结构下方
    "text": "<formatted>"
}
```

```json
// multiblock 参数接受的 json 格式
{
    // 该多方块结构所需的方块
    // 每个方块对应一个字母，类似合成表那种写法
    // 内置：空气方块对应空格，任意方块对应 _
    // 中括号区分状态值，逗号分隔
    "mapping": {
        "G": "minecraft:gold_block",
        "R": "minecraft:stained_hardened_clay[color=red]",
        "W": "minecraft:wool",
        "0": "minecraft:glass"
    },
    // 摆放顺序，自上而下
    // 数字 0 代表结构体中心，默认映射到 air，可在 mapping 中修改
    "pattern": [
    	[ " GRG ", "GGRGG", "RRRRR", "GGRGG", " GRG " ], 
    	[ "GG GG", "G   G", "     ", "G   G", "GG GG" ],
    	[ "G   G", "     ", "     ", "     ", "G   G" ], 
    	[ "G   G", "     ", "  0  ", "     ", "G   G" ], 
    	[ "_WWW_", "WWWWW", "WWWWW", "WWWWW", "_WWW_" ]
	],
    // 中心对称。设置为 true 则不会检查旋转，性能更好
    "symmetrical": false,
    // 数组[X, Y, Z]，设定结构体中心 0 的偏移量
    "offset": [0, 0, 0]
}
```



#### entity

> 显示实体

```json
{
    // 实体 id。可添加 nbt
    "entity": "<entity_id>[(nbt)]",
    // 比例缩放。负值将上下颠倒
    "scale": 1.0,
    // 上下移动
    "offset": 1.0,
    // 是否旋转
    "rotate": true,
    // 当 rotate=false 时，决定渲染角度
    "default_rotation": -45.0,
    // 显示名称。当其为空或者未定义时，使用实体名称
    "name": "<string>",
    // 格式化字符串 显示在实体下方
    "text": "<formatted>"
}
```



#### spotlight

> 展示物品

```json
{
    // 必选 ItemStackString 展示的物品 
    "item": "<item>",
    // 显示于物品顶端，留空或未定义则使用物品名
    "title": "<string>",
    // 配方页面。当值为 true 时，其他地方显示该物品时，按 shift 点击则会跳转到该页面
    // 常用于显示某些特殊的合成方法
    "link_recipe": false,
    // 格式化字符串 显示于物品下方
    "text": "<formatted>"
}
```



#### link

> 链接
>
> 继承于 text，拥有 text 的所有用法，并在底部显示一个按钮

```json
{
    // 必选 链接地址
    "url": "<string>",
    // 必选 按钮文字
    "link_text": "<string>"
}
```



#### relations

> 跳转到其他页面的页面

```json
{
    // 链接到的章节id数组
    "entries": ["<entry_id>"],
    // 标题，若为空或未定义，则显示 Related Chapters
    "title": "<string>",
    // 格式化字符串，显示在链接下方
    "text": "<formatted>"
}
```



#### quest

> 任务完成后，任务页面将会显示选中标记
>
> 已完成任务将会显示在列表末尾
>
> 一个章节中只有一个任务
>
> 建议配合 turnin 使用

```json
{
    // 触发器，完成任务的进度
    // 留空或未定义则显示 "tigger" 按钮
    "trigger": "",
    // 标题。留空或未定义则显示 "Objective
    "title": "<string>",
    // 显示文本
    "text": "<formatted>"
}
```



#### empty

> 仅仅一个空页面

```json
{
    // false 则显示一个没有任何填充的空白页
    // 否则还会填充 BookJson#filler_texture
    "draw_filler": true
}
```



#### 自定义样板

> 使用自定义样板
>
> 样板位于 

##### 格式

```json
{
    // 导入其他模板
    "include": [
        {
            "template": "<template_id>",
            // 别名，下面 components 中 type 使用该值
            "as": "<string>",
            // 绑定变量
            "using": {
                "key": "value|<variable>"
            }
        }, ...
    ],
    // 组成组件 详见 基本文件格式/Entry/type/自定义样板/可用组件
    "components" [{}]
}
```



##### 可用组件

######  通用属性

```json
{
    // 必填 组件类型
    "type": "<string>",
    // int 在当前页面水平/垂直位置
    "x": 0,
    "y": 0,
    // 当该进度未完成时，锁定该组件
    "advancement": "<advancement>",
    // 当 advancement 进度未完成时，是否显示该组件
    "negate_advancement": false,
    // 根据 flag 决定是否显示该组件
    "flag": "<flag>",
    // 仅用于 mod，用于利用 ComponmentProcessors 在代码层面上隐藏组件
    "group": "<string>",
    // 一个函数，返回 false 或留空则不显示该组件
    "guard": "<function>"
}
```

###### text

> 显示文本，支持格式化

```json
{
    // 必选 该 page 显示的文字 格式化字符串
    // 可以是变量？
    "text": "<formatted>|<variable>",
    // 字体颜色。默认为 book.json 的 text_color 属性
    "color": "000000",
    // 文本最大宽度，默认为该页面的宽度
    "max_width": 0,
    // 行高，只是行高，不调整字体大小
    "line_height": 9
}
```



###### item

> 表示一个物品，鼠标悬停可以查看提示，如果有的话shift点击可以打开配方页面

```json
{
    // 必选 除了 <item>，还可以使用 ore:ORENAME 检索矿物词典，或使用逗号分隔显示多个物品
    "item": "<item>|<string>|,|<variable>",
    // 是否显示边框，效果见各种配方页面
    "framed": false,
    // 将所在页面设置为该物品的配方页面
    "link_recipe": false
}
```



###### image

> 绘制完整或部分图像

```json
{
    // 必选 图片长/宽应为 2 的指数
    "image": "<resource>|<variable>",
    // 必选 截取原始图片区域的长/宽度
    "width": 0,
    "height": 0,
    // 从原始图片左/上方某个距离开始绘制
    "u": 0,
    "v": 0,
    // 要绘制到屏幕上的图像的长/宽度
    "texture_width": 256,
    "texture_height": 256,
    // 图片缩放比例
    "scale": 1.0
}
```



###### header

> 绘制标题，不绘制分割线
>
> x y 参数可以标注为 -1，-1 为 default pages 的位置

```json
{
    // 必选 标题文本
    "text": "<string>|<variable>",
    // 标题颜色 默认为 book.json 的 header_color
    "color": "<color>",
    // 是否居中对齐，否则左对齐
    "centered": true,
    // 缩放
    "scale": 1.0
}
```



###### entity

> 显示一个实体

```json
{
    // 实体 id
    "entity": "<entity_id>[(nbt)]|<variable>",
    // 实体渲染尺寸，正方形边长
    "render_size": 100,
    // 实体是否旋转
    "rotate": true,
    // rotate=false 时，该实体渲染角度
    "default_rotation": -45.0
}
```



###### separator

> 绘制分割线，无任何其他参数
>
> x y 参数可以标注为 -1，-1 为 default pages 的位置

###### frame

> 绘制一个 200*200 的图片，使用 book 的材质，就像图片边框一样
>
> 无任何其他参数
>
> x y 参数可以标注为 -1，-1 为 default pages 的位置

###### tooltip

> 当鼠标悬停到此处时，渲染一个提示框

```java
{
    // 必选 提示文字将逐一显示；使用 & 代替 §
    "tooltip": ["<string>|<variable>"],
    // 必选 提示框长宽
    "width": 0,
    "height": 0
}
```



###### custom

> 自定义组件
>
> 除了 class，还可以有在 ICustomComponent 里面的自定义属性

```java
{
    // ICustomComponent 实现类
    "class": "<string>"
}
```



# 其他相关格式

## Text Formatting

- $() 清除所有格式

- $(br) 换行

- $(br2) 换2行

- $(li) 列表

  > 嵌套：$(li2) $(li3) ...

- $(#rgb) RGB颜色代码(16进制)，RRGGBB/RGB

- $(0-f) minecraft 颜色代码

- $(k/l/m/n/o) 字体格式化代码

- $(l:entry_id) 内部链接，连接到指定章节

- $(l:entry_id#anchor) 内部链接，连接到指定章节，匹配章节 page#anchor 字段

- $(l:\<url>)${/l} 外部链接 打开网站

- $(playername) 插入当前玩家用户名

- $(k:\<key>) 插入快捷键 key_key.left:30

  > 在 options.txt 中找到对应的键位，格式应当为 key_key.[key.]\<key>:key_code，则在括号内填入 \<key> 对应的值

- $(t:\<tooltip>)$(/t) 插入鼠标悬停文本作为提示，提示文本为 \<tooltip> 

## 宏

- $(obf) -> $(k)

- $(bold) -> $(l)

- $(strike) -> $(m)

- $(italic) -> $(o)

- $(italics) -> $(o)

- $(list -> $(li

  - *(the lack of closing parenthesis is intended to allow you to do $(list2), $(list3), etc)*

- $(reset) -> $()

- $(clear) -> $()

- $(2br) -> $(br2)

- $(p) -> $(br2)

- /$ -> $()

- \<br> -> $(br)

- $(nocolor) -> $(0)

- $(item) -> $(#b0b)

- $(thing) -> $(#490)

- 自定义宏

  ```json
  // 添加到 book.json 的 macros 节
  "macros" {
      // 替换指定字符串
      "key1": "value1",
      "key2": "value2",
  }
  ```

  

## ItemStack String

> 用于创建 ItemStack，作为显示图标等
>
> nbt 注意转义

\<mod_id>:\<item_id>\[:damage]\[#count]\[(nbt)]

## ConfigFlags

> 当 flag 为 false 时，隐藏标记所在内容

### 标志

- debug：从 IDE 打开游戏时为 true

- advancements_disabled：Patchouli 配置的 Disable Advancement Locking 值

- testing_mode：Patchouli 配置的 Testing Mode 值

- mod:\<mod_id>：游戏中加载了对应 mod 则为 true

- 自定义：需要通过 mod-api 添加

  ```java
  PatchouliAPI.instance.setConfigFlag("name", value)
  ```

### 语法

- !flag
- &flag1,flag2,flag3,...
- |flag1,flag2,flag3,...

## Function

### 内置函数

- iname：输入 ItemStack，输出显示名
- icount：输入 ItemStack，输出物品数量
- ename：输入实体id，输出实体名称
- upper：输入字符串，输出大写字符串
- lower：输入字符串，输出小写字符串
- trim：输入字符串，输出去除前后空格的字符串
- capital：输入字符串，当全部为小写时，输出单词首字母大写的字符串
- fcapital：输入字符串，输出单词首字母大写的字符串
- exists：判断输入变量值是否非空
- iexists：判断输入 ItemStack 物品栈是否非空
- inv：Boolean 取反

# 使用书籍 Item

> 总之 就是一个 patchouli:guide_book 附加一个 patchouli:book nbt

## CraftTweaker

>\<patchouli:guide_book>.withTag({"patchouli:book": "<book_id>"})

## 原版合成表/成就

```json
{
    "item": "patchouli:guide_book",
    "nbt": {
        "patchouli:book": "<book_id>"
    }
}
```

