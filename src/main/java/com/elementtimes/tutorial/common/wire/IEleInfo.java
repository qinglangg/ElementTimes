/**   
* @Title: IEleInfo.java
* @Package minedreams.mi.api.electricity
* @author EmptyDreams
* @date 2019年10月1日 下午9:32:30
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire;

/**
 * 存储电力方块的信息
 * @author EmptyDremas
 * @version 1.0
 */
public interface IEleInfo {

	/**
	 * 判断方块是否可以连接电线，
	 * <b>注意：此方法不保证fromPos/nowPos在此时已经在世界存在，所以提供了额外的参数</b>
	 * @param info 附加信息
	 * @param nowIsExist 当前方块是否存在
	 * @param fromIsExist 调用方块是否存在
	 */
	boolean canLink(LinkInfo info, boolean nowIsExist, boolean fromIsExist);
	
}
