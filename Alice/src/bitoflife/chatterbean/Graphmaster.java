/*
Copyleft (C) 2005 H锟絣io Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
 */

package bitoflife.chatterbean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bitoflife.chatterbean.aiml.Category;

public class Graphmaster {
	/*
	 * Attributes
	 */

	/* The children of this node. */
	private final Map<String, Graphmaster> children = new HashMap<String, Graphmaster>();

	private int size = 0;
	private Graphmaster parent;
	private Category category;
	private String name; // The name of a node is the pattern element it
							// represents.

	private Graphmaster(String name) {
		this.name = name;
	}

	/**
	 * Constructs a new root node.
	 */
	public Graphmaster() {
	}

	/**
	 * Constructs a new tree, with this node as the root.
	 */
	public Graphmaster(List<Category> categories) {
		append(categories);
	}

	/*
	 * Methods
	 */
	/**
	 * 鐢变簬MatchPath(涔熷氨鏄繖閲岀殑elements鍙傛暟)鏄寜鐓э細Pattern锛宼hat,topic鐨勯『搴忔潵鐨勶紝鑰�	 * child.parent鏄弽鍚戞寚瀹氱殑锛屾墍浠ユ垜鎰熻锛屽湪杩涜鍜岃緭鍏ュ尮閰嶇殑鏃跺�锛屽簲璇ユ槸浠巘opic寮�鍖归厤锛岀劧鍚�	 * 鎵嶈兘閫氳繃parent鏉ユ壘鍒颁粬鐨勪笅涓�釜鍖归厤椤广�鏄繖鏍峰悧锛熼偅杩欎釜澶т綋鐨勯『搴忓鏋滄槸鍙嶈�鍖归厤鐨勶紝閭ｉ噷闈㈢殑鍏冪礌 鏄笉鏄湪澶勭悊鐨勬椂鍊欎篃鍙嶈�鏉ョ殑鍛紵
	 * 濡傛灉鏄紝閭ｆ垜鐨勬劅瑙夊氨鏄鐨勶紝濡傛灉涓嶆槸锛岄偅鏄�涔堟悶鐨勫憿锛�	 * 
	 * 杩欎釜鍑芥暟鏄繖鏍峰鐞嗙殑锛氬鏋滄壘鍒颁簡鐩稿簲鐨別lement閭ｄ箞灏辨壘涓嬩竴涓紝濡傛灉娌℃湁鎵惧埌灏眓ew涓�釜child
	 * 鐒跺悗鎶婅繖涓猚hild鍔犺繘鍘伙紝鍦ㄥ悗鎸囧畾璇hild鐨刾arent銆�	 * 
	 * 璁╂垜鐤戞儜鐨勬槸锛岃繖涓摼琛ㄦ槸鍚戝墠鐨勶紝渚嬪锛氣�浣�鐨勪富浜烘槸璋佲�鐨勯摼琛ㄥ舰寮忥細浣�-*<-鐨�-涓�-浜�-鏄�-璋�	 * 涔熷氨鏄姣忎竴涓猠lement閮借兘鎵惧埌浠栫殑parent浠庤�鎶婁竴鏉￠摼鎷庡嚭鏉ワ紝浣嗘槸杩欐牱鎬庢牱浠庡ご鍖归厤鍛紵锛�	 * 
	 * @param category
	 * @param elements
	 * @param index
	 */
	private void append(Category category, String[] elements, int index) {
		Graphmaster child = children.get(elements[index]);
		if (child == null)
			appendChild(child = new Graphmaster(elements[index]));

		int nextIndex = index + 1;
		if (elements.length <= nextIndex)
			child.category = category;
		else
			child.append(category, elements, nextIndex);// 閫掑綊璋冪敤銆�	
		}

	private void appendChild(Graphmaster child) {
		children.put(child.name, child);
		child.parent = this;// 杩欓噷鐨則his鎸囩殑鏄皟鐢ㄨ鏂规硶鐨勫璞★紝涔熷氨鏄杩欓噷鐨刾arent鎸囧悜鍓嶄竴涓猚hild锛屽叾瀹炲氨鐩稿綋涓庝竴涓摼琛ㄣ�
	}

	/**
	 * <p>
	 * Returns an array with three child nodes, in the following order:
	 * </p>
	 * <ul>
	 * <li>The "_" node;</li>
	 * <li>The node with the given name;</li>
	 * <li>The "*" node.</li>
	 * </ul>
	 * <p>
	 * If any of these nodes can not be found among this node's children, its
	 * position is filled by <code>null</code>.
	 * </p>
	 */
	private Graphmaster[] children(String name) {
		// ##############
		// System.out.println("name:" + name);
		return new Graphmaster[] { children.get("_"), children.get(name),// 杩欓噷涓轰粈涔堣寰楀埌"_"鍜屸�*鈥濆憿锛燂紵锛�				
				children.get("*") };
		}

	private boolean isWildcard() {
		return ("_".equals(name) || "*".equals(name));
	}

	private Category match(Match match, int index) {
		if (isWildcard()) {
			return matchWildcard(match, index); // 杩欎釜鍒嗘敮杩樹笉娓呮?????
		}
		if (!name.equals(match.getMatchPathByIndex(index)))// 鎴戣寰楄繖涓垎鏀病鏈夊繀瑕侊紒鍥犱负鏃㈢劧鑳借繘杩欎釜鍑芥暟锛屽鏋滀笉鏄�閰嶇鐨勮瘽锛屽氨涓�畾鏄浉绛夌殑銆�			
			return null;

		int nextIndex = index + 1;
		if (match.getMatchPathLength() <= nextIndex)
			return category;

		return matchChildren(match, nextIndex);
	}

	private Category matchChildren(Match match, int nextIndex) {
		Graphmaster[] nodes = children(match.getMatchPathByIndex(nextIndex));
		for (int i = 0, n = nodes.length; i < n; i++) {

			Category category = (nodes[i] != null ? nodes[i].match(match,// 杩欎釜node[i]浣垮緱瀵硅薄涓�釜涓�釜寰�笅闈紶閫掋�
					nextIndex) : null);

			if (category != null)
				return category;
		}

		return null;
	}

	private Category matchWildcard(Match match, int index) {
		int n = match.getMatchPathLength();
		for (int i = index; i < n; i++) {
			Category category = matchChildren(match, i);
			if (category != null) {
				match.appendWildcard(index, i);// 杩欎釜鍑芥暟鐨勫姛鑳藉氨鏄壘鍑猴紝*鍙锋墍鍖归厤鐨勫唴瀹癸紙鍖呮嫭input涓紝that涓紝杩樻湁topic涓級
				return category;
			}
		}

		if (category != null)
			match.appendWildcard(index, n);
		return category;
	}

	public void append(List<Category> categories) {
		for (Category category : categories)
			append(category);
	}

	public void append(Category category) {
		String matchPath[] = category.getMatchPath();
		// for (String string : matchPath) {
		// System.out.print(string + "|");
		// }
		// System.out.println("--------matchPath:");
		// for (String string : matchPath) {
		// System.out.println(string);
		// }
		append(category, matchPath, 0);
		size++;
	}

	/**
	 * Returns the Catgeory which Pattern matches the given Sentence, or
	 * <code>null</code> if it cannot be found.
	 */
	public Category match(Match match) {
		return matchChildren(match, 0);
	}

	public int size() {
		return size;
	}

	public String toString() {
		return "[name]:" + name;
	}
}
