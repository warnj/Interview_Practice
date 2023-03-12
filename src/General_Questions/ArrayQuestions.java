package General_Questions;

import java.awt.*;
import java.math.BigInteger;
import java.util.*;
import java.util.List;

public class ArrayQuestions {

    public static void main(String[] args) {
    }

    // https://leetcode.com/problems/fruit-into-baskets
    // O(n) time and O(1) space - sliding window
    public static int totalFruit(int[] fruits) {
        int lo = 0, max = 1; // window [lo,hi) is valid fruit picking order
        Map<Integer,Integer> s = new HashMap<>(); // int -> count(int), max size 2
        for (int hi = 0; hi < fruits.length; ) {
            if (s.size() < 2 || s.containsKey(fruits[hi])) { // expand window from right
                s.put(fruits[hi], s.getOrDefault(fruits[hi], 0) + 1);
                hi++;
                max = Math.max(max, hi-lo);
            } else { // shrink window from left
                int count = s.get(fruits[lo]);
                if (count == 1) s.remove(fruits[lo]);
                else s.put(fruits[lo], count - 1);
                lo++;
            }
        }
        return max;
    }

    // https://leetcode.com/problems/maximum-sum-circular-subarray
    public static int maxSubarraySumCircular(int[] nums) {

        return -1;
    }
    // O(n^2) solution
    public static int maxSubarraySumCircularBrute(int[] nums) {
        int max = Integer.MIN_VALUE;
        int n = nums.length;
        for (int i = 0; i < n; i++) { // start index
            int sum = nums[i];
            max = Math.max(max, sum);
            for (int j = 1; j < n; j++) { // length of possible subarray
                sum += nums[(i + j) % n];
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    // https://leetcode.com/problems/maximum-ice-cream-bars
    public static int maxIceCream(int[] costs, int coins) {
        Arrays.sort(costs);
        int count = 0;
        int i = 0;
        while (i < costs.length && coins > 0) {
            coins -= costs[i++];
            if (coins >= 0) count++;
        }
        return count;
    }

    // https://leetcode.com/problems/first-missing-positive/
    public static int firstMissingPositive(int[] nums) {
        // if !contains(1), return 1
        // else if !contains(2) return 2
        // else if ...
        int n = nums.length;
        // 1. mark numbers (num < 0) and (num > n) with a special marker number (n+1)
        // (we can ignore those because if all number are > n then we'll simply return 1)
        for (int i = 0; i < n; i++)
            if (nums[i] <= 0 || nums[i] > n)
                nums[i] = n + 1;
        // note: all number in the array are now positive, and on the range 1..n+1
        // 2. mark each cell appearing in the array, by converting the index for that number to negative
        for (int i = 0; i < n; i++) {
            int num = Math.abs(nums[i]);
            if (num > n) continue;
            num--; // -1 for zero index based array (so the number 1 will be at pos 0)
            if (nums[num] > 0) { // prevents double negative operations
                nums[num] = -nums[num];
            }
        }
        // 3. find the first cell which isn't negative (doesn't appear in the array)
        for (int i = 0; i < n; i++)
            if (nums[i] >= 0)
                return i + 1;
        // 4. no positive numbers were found, which means the array contains all numbers 1..n
        return n + 1;
    }

    // Remove all duplicates that occur in the given sorted array, return the length of the new array
    public static int removeDuplicatesOne(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 1 || n > nums[i - 1])
                nums[i++] = n;
        return i;
    }

    // Remove all duplicates that occur more than twice in the given sorted array.
    // https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
    public static int removeMoreThanTwoDuplicates(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 2 || n > nums[i - 2])
                nums[i++] = n;
        return i;
    }

    // https://leetcode.com/problems/longest-increasing-subsequence
    public static int lengthOfLIS(int[] nums) {
        List<Integer> subs = new ArrayList<>(); // the actual LIS (sorted)
        for (int i = 0; i < nums.length; i++) {
            if (i == 0 || nums[i] > subs.get(subs.size() - 1)) { // expand the max subarray
                subs.add(nums[i]);
            } else { // replace within the subarray to make the numbers tight (ending numbers as large as possible)
                int index = findEqualOrLarger(subs, nums[i]);
                subs.set(index, nums[i]);
            }
        }
        return subs.size();
    }
    // can do binary search here for nlogn total time
    private static int findEqualOrLarger(List<Integer> list, int target) {
        int i = 0;
        while (i < list.size() && list.get(i) < target) i++;
        return i;
    }
    // O(n^2) time, O(n) space
    public static int lengthOfLISn2(int[] nums) {
        int[] dp = new int[nums.length]; // dp[i] = len of longest increasing seq ending at i
        dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            int maxLesser = 1; // 1 + max of the saved LIS before i for nums less than current num (where num increases the LIS)
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i])
                    maxLesser = Math.max(maxLesser, dp[j] + 1);
            }
            dp[i] = maxLesser;
        }
        int max = dp[0]; // find the max, may not be at the end
        for (int i = 1; i < dp.length; i++)
            max = Math.max(max, dp[i]);
        return max;
    }

    public void moveZeroes(int[] nums) {
        int lo = 0; // beginning of zeros
        int cur = 0;
        // if find a non-zero, move it to the front
        while (cur < nums.length) {
            while (cur < nums.length && nums[cur] == 0) cur++;
            if (cur < nums.length) {
                // swap non-zero into location of first zero
                if (lo != cur) {
                    int temp = nums[lo];
                    nums[lo] = nums[cur];
                    nums[cur] = temp;
                }
                lo++;
                cur++;
            }
        }
    }

    // https://leetcode.com/problems/minimum-size-subarray-sum/
    public int minSubArrayLen(int target, int[] nums) {
        // two pointer
        int min = Integer.MAX_VALUE;
        int lo = 0;
        int hi = 0;
        int sum = nums[0];
        while (lo <= hi && hi < nums.length) {
            if (sum >= target) {
                int len = hi - lo + 1;
                if (len < min) min = len;
                sum -= nums[lo];
                lo++; // shrink window
                if (lo > hi) hi++;
            } else {//if (sum < target) {
                hi++; // expand window
                if (hi < nums.length) sum += nums[hi];
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    // https://leetcode.com/problems/longest-consecutive-sequence/
    // easy way is to sort it and iterate, problem requires O(n) time
    public static int longestConsecutive(int[] nums) {
        Set<Integer> s = new HashSet<>();
        for (int n : nums) {
            s.add(n);
        }
        int max = 0;
        for (int n : s) { // can itr through nums, but s will be faster if there are lots of duplicates
            // only attempt to build sequences from numbers that are not already part of a longer sequence
            if (!s.contains(n - 1)) { // this is the trick that takes O(n^2) to O(n) time
                int len = 1;
                while (s.contains(n + 1)) {
                    len++;
                    n++;
                }
                if (len > max) max = len;
            }
        }
        return max;
    }
    // O(nlogn) time, O(1) space, and modifies the given array
    public static int longestConsecutiveSimple(int[] nums) {
        if (nums.length == 0) return 0;
        Arrays.sort(nums);
        int max = 1; // longest seq length
        int curMax = 1; // current seq length
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i-1]) { // skip equal numbers, but prev seq is still valid
                while (i < nums.length && nums[i] == nums[i-1]) i++;
                if (i >= nums.length) return max;
            }
            if (nums[i] == nums[i-1] + 1) {
                curMax++;
                max = Math.max(max, curMax);
            } else {
                curMax = 1;
            }
        }
        return max;
    }
    // misses a few corner cases, passes 67 of 70 tests
    public static int longestConsecutiveWrong(int[] nums) {
        // treat it like an interval problem
        if (nums.length == 0) return 0;
        Map<Integer, int[]> m = new HashMap<>(); // map num to its consecutive interval
        for (int i : nums) {
            m.put(i, new int[]{i, i}); // lo and hi are inclusive
        }
        for (int i : nums) {
            // merge intervals with any present consecutive numbers, watch out for duplicates
            int[] interval = m.get(i);
            if (m.containsKey(i - 1) && m.containsKey(i + 1)) { // merge three intervals
                int[] k1 = m.get(i - 1);
                int[] k2 = m.get(i + 1);
                int min = Math.min(Math.min(k1[0], k2[0]), interval[0]);
                int max = Math.max(Math.max(k1[1], k2[1]), interval[1]);
                interval[0] = min;
                interval[1] = max;
                k1[0] = min;
                k1[1] = max;
                k2[0] = min;
                k2[1] = max;
            } else { // merge applicable pairs of intervals
                if (m.containsKey(i - 1)) {
                    int[] k = m.get(i - 1);
                    int min = Math.min(interval[0], k[0]);
                    int max = Math.max(interval[1], k[1]);
                    interval[0] = min;
                    interval[1] = max;
                    k[0] = min;
                    k[1] = max;
                }
                if (m.containsKey(i + 1)) {
                    int[] k = m.get(i + 1);
                    int min = Math.min(interval[0], k[0]);
                    int max = Math.max(interval[1], k[1]);
                    interval[0] = min;
                    interval[1] = max;
                    k[0] = min;
                    k[1] = max;
                }
            }
            // don't need to put k or interval back in map since int[] is pointer to real array
        }
        int max = Integer.MIN_VALUE;
        for (int[] interval : m.values()) {
            max = Math.max(max, interval[1] - interval[0] + 1);
        }
        return max;
    }

    // compress the range of shelves in input to smallest range starting at 1 maintaining distinct numbers & order
    // O(nlogn + n) time and O(n) space
    public static List<Integer> assignShelves(List<Integer> shelves) {
        Map<Integer, List<Integer>> vals = new HashMap<>(); // val -> list of indexes with the val
        for (int i = 0; i < shelves.size(); i++) {
            int n = shelves.get(i);
            List<Integer> indexes = vals.get(n);
            if (indexes == null) {
                indexes = new ArrayList<>();
            }
            indexes.add(i);
            vals.put(n, indexes);
        }
        Collections.sort(shelves); // could create deep copy and sort to keep shelves unchanged
        List<Integer> result = new ArrayList<>(shelves); // ensure result has number of elements it will end up with
        int cur = 1; // the flattened value
        for (int i = 0; i < shelves.size(); i++) {
            int n = shelves.get(i);
            if (i > 0 && n == shelves.get(i - 1)) continue; // skip duplicates since they're already filled
            List<Integer> indexes = vals.get(n);
            for (int ind : indexes) { // deal with duplicates, outer for still runs in O(n) time
                result.set(ind, cur);
            }
            cur++;
        }
        return result;
    }

    // https://leetcode.com/problems/search-in-rotated-sorted-array/
    // doing "normal" binary search with extra conditions to check for inflection location
    public static int search2(int[] nums, int target) {
        int n = nums.length;
        int lo = 0;
        int hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == nums[mid]) return mid;
            if (target == nums[lo]) return lo; // little sloppy to have these checks, but it works
            if (target == nums[hi]) return hi;
            if (nums[lo] < nums[hi]) { // entire array/sub-array sorted
                if (target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else if (nums[lo] < nums[mid]) { // lower half sorted
                if (target < nums[mid] && target > nums[lo]) { // target is in it
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else { // upper half sorted
                if (target > nums[mid] && target < nums[hi]) { // target is in it
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }

    // find min, then do "normal" binary search using known inflection point for calculating the mid between lo and hi
    public static int search(int[] nums, int target) {
        int min = findMinIndex(nums);
        int lo = 0;
        int hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int realmid = (mid + min) % nums.length;
            if (nums[realmid] == target) return realmid;
            if (nums[realmid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }

    public static int findMinIndex(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;
        int lo = 0;
        int hi = n - 1;
        if (nums[lo] < nums[hi]) return lo; // must be sorted
        int mid = lo + (hi - lo) / 2;
        while (lo < mid) {
            if (nums[lo] > nums[mid]) { // search lower half
                hi = mid;
            } else { // search upper half
                lo = mid;
            }
            mid = lo + (hi - lo) / 2;
        }
        return hi;
    }

    // https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
    public static int findMin(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        int lo = 0;
        int hi = n - 1;
        if (nums[lo] < nums[hi]) return nums[lo]; // must be sorted
        int mid = lo + (hi - lo) / 2;
        while (lo < mid) {
            if (nums[lo] > nums[mid]) { // search lower half
                hi = mid;
            } else { // search upper half
                lo = mid;
            }
            mid = lo + (hi - lo) / 2;
        }
        return nums[hi];
    }

    // https://leetcode.com/problems/maximum-subarray/
    public static int maxSubArray(int[] nums) {
        // could allocate an int 'cur', instead using nums as the extra space
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] + nums[i] > nums[i]) {
                nums[i] = nums[i - 1] + nums[i];
            }
            if (nums[i] > max) max = nums[i];
        }
        return max;
    }
    // maxSubArray of a non-circular array
    public static int kadaneAlgo(int[] nums) {
        int max = Integer.MIN_VALUE;
        int localMax = 0;
        for (int i = 0; i < nums.length; i++) {
            localMax = Math.max(nums[i], localMax + nums[i]);
            max = Math.max(localMax, max);
        }
        return max;
    }

    // https://leetcode.com/problems/delete-and-earn/
    public static int deleteAndEarn(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int n : nums) {
            counts.put(n, 1 + counts.getOrDefault(n, 0));
        }

        int result = 0;

        return result;
    }

    // https://leetcode.com/problems/arithmetic-slices/
    public static int numberOfArithmeticSlices(int[] nums) {
        int result = 0;
        if (nums.length < 3) return result;
        int lo = 0;
        int hi = 2;
        int dif = nums[1] - nums[0];
        while (hi < nums.length) {
            while (hi < nums.length && nums[hi] - nums[hi - 1] == dif) {
                result++;
                hi++;
            }
            lo++;
            // gap from [lo, hi-1] has all same difference - do some math
            result += countSubarraysOf3(hi - lo);
            if (hi < nums.length) {
                dif = nums[hi] - nums[hi - 1];
                lo = hi - 1;
                hi++;
            }
        }
        return result;
    }

    // returns number of subarrays of size 3 in an array of given size - there is probably a better math way to do this
    private static int countSubarraysOf3(int size) {
        if (size < 3) return 0;
        int[] dp = new int[size + 1];
        dp[3] = 1;
        int gap = 2;
        for (int i = 4; i < size + 1; i++, gap++) {
            dp[i] = dp[i - 1] + gap;
        }
        return dp[size];
    }

    // https://leetcode.com/problems/median-of-two-sorted-arrays/
    // problem screams binary search, which works comparing middles: if (aMid < bMid) Keep[aRight+bLeft] else Keep[bRight+aLeft]
    // O(n) solution doing what a human would do eliminating highest and lowest until reaching middle
    public static double findMedianSortedArrays(int[] a, int[] b) {
        if (a.length == 0 && b.length == 0) return -1.0;
        if (a.length == 0)
            return b.length % 2 == 1 ? b[b.length / 2] : ((double) b[b.length / 2] + b[b.length / 2 - 1]) / 2;
        if (b.length == 0)
            return a.length % 2 == 1 ? a[a.length / 2] : ((double) a[a.length / 2] + a[a.length / 2 - 1]) / 2;
        int aLo = 0, bLo = 0;
        int aHi = a.length - 1, bHi = b.length - 1;
        int total = a.length + b.length;
        for (int i = 0; i < (total - 1) / 2; i++) { // stop one early so Lo and Hi pointers will be at final 1 or 2 numbers
            // cross off lowest and highest
            if (aLo <= aHi && bLo <= bHi) { // both arrays have valid pointers
                if (a[aLo] < b[bLo]) aLo++;
                else bLo++;
                if (a[aHi] < b[bHi]) bHi--;
                else aHi--;
            } else if (aLo <= aHi) { // only a has valid pointers
                aLo++;
                aHi--;
            } else { // bLo <= bHi  // only b has valid pointers
                bLo++;
                bHi--;
            }
        }
        if (total % 2 == 1)
            return aLo <= aHi ? a[aLo] : b[bLo]; // return current valid pointer, lo and hi will be same, this line optional
        if (aLo <= aHi && bLo <= bHi) {
            return ((double) a[aLo] + b[bLo]) / 2; // aLo==aHi & bLo==bHi, so same as (a[aHi] + b[bHi]) / 2
        } else if (aLo <= aHi) {
            return ((double) a[aLo] + a[aHi]) / 2;
        } else { // bLo <= bHi
            return ((double) b[bLo] + b[bHi]) / 2;
        }
    }

    // https://leetcode.com/problems/majority-element-ii/
    // given integer array of size n, find all elements that appear more than n/3 times
    // O(n) time, O(1) space - Boyer-Moore Voting Algorithm
    // todo: unfinished
    public static List<Integer> majorityElement(int[] nums) {
        // find one element that appears more than n/3 times, then try again ignoring the element from first round, can
        // be at most 2 answers in resulting list
        List<Integer> result = new ArrayList<>();
        int sum = 0;
        int candidate = 0;
        for (int num : nums) {
            if (sum == 0) {
                candidate = num;
                sum = 2;
            } else if (num == candidate) {
                sum += 2; // add 2 whenever we see candidate since 2 non-candidates are allowed for each correct candidate
            } else {
                sum--;
            }
        }
        if (sum > 2) {
            System.out.println("Adding result from first calculation: " + candidate);
            result.add(candidate);
        }
        if (result.isEmpty()) return result; // no element found over 1/3 frequency

        // need to find the first candidate that is not what we already have
        int i = 0;
        while (i < nums.length && nums[i] == result.get(0)) i++;
        if (i == nums.length) return result; // result is the only element in array
        sum = -i;
        candidate = nums[i];
        for (i = i + 1; i < nums.length; i++) {
            if (nums[i] == result.get(0)) { // skip the known result
                sum--;
            } else if (sum == 0) {
                candidate = nums[i];
                sum = 2;
            } else if (nums[i] == candidate) {
                sum += 2;
            } else {
                sum--;
            }
        }
        if (sum > 2) {
            System.out.println("Adding result from second calculation: " + candidate);
            result.add(candidate);
        }
        return result;
    }
    // https://leetcode.com/problems/majority-element
     public static int majorityElementOneBest(int[] nums) {
        int count = 0;
        Integer candidate = null;
        for (int num : nums) {
            if (count == 0) candidate = num;
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }
    // O(n) time and space
    public static List<Integer> majorityElementOriginal(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        List<Integer> results = new ArrayList<>();
        for (int n : nums) {
            int c = counts.getOrDefault(n, 0);
            counts.put(n, ++c);
        }
        for (Integer n : counts.keySet()) {
            Integer c = counts.get(n);
            if (c > nums.length / 3) {
                results.add(n);
            }
        }
        return results;
    }

    // https://leetcode.com/problems/product-of-array-except-self/
    // 1st option to find product of entire array, then divide by nums[i] - division prohibited, /0 may cause issues and entire product may cause overflow
    // O(n) time, O(1) extra space
    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        if (n <= 1) return null;
        int[] result = new int[n];
        result[n - 1] = 1; // holds product of the items to the right of i
        // avoid allocating a new suffix array by placing suffixes in the result array
        for (int i = n - 2; i >= 0; i--) {
            result[i] = result[i + 1] * nums[i + 1];
        }
        int pre = 1; // product of terms before current i
        for (int i = 1; i < nums.length; i++) {
            pre *= nums[i - 1];
            result[i] *= pre;
        }
        return result;
    }
    // O(n) time and space
    public static int[] productExceptSelfDP(int[] nums) {
        int n = nums.length;
        if (n <= 1) return null;
        int[] result = new int[n];
        int[] suf = new int[n]; // product of terms after suf[i] (suf[n-1] for initialization only)
        suf[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            suf[i] = suf[i + 1] * nums[i + 1];
        }
        result[0] = suf[0];
        int pre = 1; // product of terms before current i
        for (int i = 1; i < nums.length; i++) {
            pre *= nums[i - 1];
            result[i] = pre * suf[i];
        }
        // nums = [1,2,3,4] , pre = [1,1,2,6] , suf = [24,12,4,1]
        // nums = [-1,1,0,-3,3] , pre = [1,-1,-1,0,0] , suf = [0,0,-9,3,1]
        return result;
    }

    // https://leetcode.com/problems/trapping-rain-water/
    // O(n) time and O(1) space, two pointers working inward keeping track of left and right maxes
    public static int trap(int[] height) {
        if (height.length <= 2) return 0;
        int left = 1;
        int right = height.length - 2;
        int leftMax = height[0];
        int rightMax = height[height.length - 1];

        int result = 0;
        while (left <= right) {
            // the smaller max determines which pointer gets moved since the level for it is known, level for larger
            // pointer is not known since a larger middle max value may be found that changes its level
            if (leftMax < rightMax) {
                result += Math.max(0, leftMax - height[left]);
                leftMax = Math.max(leftMax, height[left]);
                left++;
            } else {
                result += Math.max(0, rightMax - height[right]);
                rightMax = Math.max(rightMax, height[right]);
                right--;
            }
        }
        return result;
    }

    // O(n^2) worst case runtime when input is sorted - O(n) recursions (search space decreases by 1 each time) and each does O(n) work
    // O(n) best case runtime, no extra space used. Average case runtime is somewhere in the middle.
    public static int trapOriginal(int[] height) {
        return trapOriginal(height, 0, height.length - 1);
    }

    // returns the amount of water trapped between given lo and hi indexes inclusive
    // some work is wasted re-searching for 2 maxes when already have looked at the numbers in previous recursive call (and one of the next maxes is either lo or hi)
    private static int trapOriginal(int[] h, int lo, int hi) {
        //		System.out.printf("%s looking between lo:%d to hi:%d\n", Arrays.toString(h), lo, hi);
        if (hi - lo <= 1) return 0; // must be of at least size 3 to be able to trap any water

        // find top two values and their indexes, they will be the edges of the water basin
        int max = Math.max(h[lo], h[lo + 1]);
        int max2 = Math.min(h[lo], h[lo + 1]); // 2nd highest between lo and hi inclusive
        int maxI = h[lo] > h[lo + 1] ? lo : lo + 1;
        int max2I = h[lo] > h[lo + 1] ? lo + 1 : lo; // index of the 2nd highest
        for (int i = lo + 2; i <= hi; i++) {
            if (h[i] > max) {
                max2 = max;
                max2I = maxI;
                max = h[i];
                maxI = i;
            } else if (h[i] > max2) {
                max2 = h[i];
                max2I = i;
            }
        }
        //		System.out.printf("Max:%d (at %d), 2nd Max:%d (at %d)\n", max, maxI, max2, max2I);
        int leftEdge = Math.min(maxI, max2I); // left side of water edge
        int rightEdge = Math.max(maxI, max2I); // right side of water edge
        int level = Math.min(max, max2);
        int water = 0;
        for (int i = leftEdge + 1; i < rightEdge; i++) {
            water += level - h[i];
        }
        //		System.out.printf("Found %d water between [%d,%d], recursing on intervals [%d,%d] and [%d,%d]\n", water, leftEdge, rightEdge, lo, leftEdge, rightEdge, hi);
        // found the water between max and 2nd max, recurse on the remaining gaps between lo and leftEdge and rightEdge and hi
        return water + trapOriginal(h, lo, leftEdge) + trapOriginal(h, rightEdge, hi);
    }

    // O(n^2) time and O(1) space
    public static int trapBrute(int[] height) {
        if (height.length <= 2) return 0;
        int result = 0;
        for (int i = 1; i < height.length - 1; i++) { // each space that may be able to hold water
            int leftMax = height[i - 1]; // find left container edge
            for (int j = i - 2; j >= 0; j--) {
                if (height[j] > leftMax) leftMax = height[j];
            }
            int rightMax = height[i + 1]; // find right container edge
            for (int j = i + 2; j < height.length; j++) {
                if (height[j] > rightMax) rightMax = height[j];
            }
            int level = Math.min(leftMax, rightMax);
            result += Math.max(0, level - height[i]);
        }
        return result;
    }

    // O(n) time and space
    // optimized by first saving the max values to the left and right of each index, which takes O(n)
    public static int trapDP(int[] height) {
        if (height.length <= 2) return 0;
        int[] leftMax = new int[height.length]; // leftMax[i] = max of all values < i for values of i from [1, length-2]
        int[] rightMax = new int[height.length];

        leftMax[0] = height[0]; // initialize, leftMax[0] won't actually be used other than to compute later leftMaxes
        for (int i = 1; i < height.length - 1; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i - 1]);
        }
        rightMax[height.length - 1] = height[height.length - 1];
        for (int i = height.length - 2; i >= 1; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i + 1]);
        }

        int result = 0;
        for (int i = 1; i < height.length - 1; i++) { // each space that may be able to hold water
            int level = Math.min(leftMax[i], rightMax[i]);
            result += Math.max(0, level - height[i]);
        }
        return result;
    }

    // https://leetcode.com/problems/top-k-frequent-elements/
    public static int[] topKFrequent(int[] nums, int k) {
        // bucket sort, O(N) time and space
        Map<Integer, Integer> counts = new HashMap<>();
        for (int n : nums) {
            counts.merge(n, 1, Integer::sum);
        }
        // array of lists, index is frequency, frequencies are not unique, so must have a list at each index
        List<Integer>[] buckets = new List[nums.length];
        for (Map.Entry<Integer, Integer> e : counts.entrySet()) {
            int freq = e.getValue(); // possible range from 1 to nums.length
            int num = e.getKey();
            List<Integer> otherFreqs = buckets[freq - 1];
            if (otherFreqs != null) {
                otherFreqs.add(num);
            } else {
                buckets[freq - 1] = new ArrayList<>();
                buckets[freq - 1].add(num);
            }
        }
        // buckets has the nums with largest frequency at the end
        int[] output = new int[k];
        int j = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            List<Integer> numsSameFreq = buckets[i];
            if (numsSameFreq != null) { // there will likely be many nulls
                for (int nI = 0; nI < numsSameFreq.size(); nI++) {
                    output[j++] = numsSameFreq.get(nI); // fill output with contents of numsSameFreq
                    if (j == k) return output;
                }
            }
        }
        return output;
    }

    public static int[] topKFrequent2(int[] nums, int k) {
        // hashmap of values -> counts, list of tuples (values,counts), reverse sort it by count, then take top k
        // O(N + n log n + k) runtime, O(n+k) space, where N is nums.length and n is the number of unique values in nums
        Map<Integer, Integer> counts = new HashMap<>();
        for (int n : nums) {
            counts.merge(n, 1, Integer::sum);
        }
        // instead of sorting list, can use heap of size k, elements being keys, sorted by vals, trick is getting comparator right:
        //		new PriorityQueue<>((n1, n2) -> count.get(n1) - count.get(n2));
        List<Tuple> sortedCounts = new ArrayList<>(counts.size());
        for (Map.Entry<Integer, Integer> e : counts.entrySet()) {
            sortedCounts.add(new Tuple(e.getKey(), e.getValue()));
        }
        Collections.sort(sortedCounts); // kinda cheating
        int[] output = new int[k];
        for (int i = 0; i < k; i++) {
            output[i] = sortedCounts.get(i).key;
        }
        return output;
    }

    static class Tuple implements Comparable<Tuple> {
        int key, val;

        public Tuple(int k, int v) {
            key = k;
            val = v;
        }

        @Override
        public int compareTo(Tuple other) {
            return other.val - val; // backwards since want most frequent
        }
    }

    // https://leetcode.com/problems/subarray-sum-equals-k/
    // no simple two pointer solution since both + and - numbers allowed
    // solution below taken from LeetCode, O(n) time and space
    // assume (D+E+3=k), sum =A+B+C+D+E+3, preSum = A+B+C, so sum-presum=k  -> algebra ->  -presum=k-sum  ->  presum=-k+sum=sum-k
    // don't care about how a presum was formed or how long it is, only care about how many particular presums exist
    public static int subarraySum(int[] nums, int k) {
        int sum = 0, result = 0;
        Map<Integer, Integer> preSum = new HashMap<>(); // map presums to their counts
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i]; // sum [0,i]
            if (sum == k)
                result++; // special case, valid subarray but wouldn't be caught by presum, can also initialize preSum with a (0 -> 1) mapping
            // sum-k = the presum that will get counts of previous ranges that with current sum will equal target, see algebra above
            if (preSum.containsKey(sum - k)) {
                result += preSum.get(sum - k);
            }
            preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
        }
        return result;
    }

    // reduce the duplicate summing by saving sums for each subarray starting position: O(n^2) runtime, O(n) space
    public static int subarraySum2(int[] nums, int target) {
        int result = 0;
        int[] sums = new int[nums.length]; // sums[k] = sum from current subarray starting position (i) to k
        for (int i = 0; i < nums.length; i++) { // starting places
            // could likely optimize a bit further by skipping zeros and doubling next additions to result
            for (int j = i; j < nums.length; j++) { // ending places
                int sum = nums[j]; // will become the sum from [i,j]
                if (j != i) { // not the first time summing from this subarray starting position
                    sum += sums[j - 1]; // add the sum of numbers that come before for current subarray starting position
                    sums[j - 1] -= nums[i]; // subtract current starting position from sum since next time starting locations will be 1 greater
                }
                sums[j] = sum;
                if (sum == target) result++;
            }
        }
        return result;
    }

    // brute force of finding all continuous subarrays and then sum: O(n^3) runtime, O(1) space
    public static int subarraySumBrute(int[] nums, int target) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) { // starting places
            for (int j = i; j < nums.length; j++) { // ending places
                int sum = 0; // sum from [i,j]
                for (int k = i; k <= j; k++) {
                    sum += nums[k];
                }
                if (sum == target) result++;
            }
        }
        return result;
    }

    // https://leetcode.com/problems/find-peak-element/
    // worst case O(log n) since no duplicates near each other
    // If num[i-1] < num[i] > num[i+1], then num[i] is peak
    // If num[i-1] < num[i] < num[i+1], then num[i+1...n-1] must contains a peak
    // If num[i-1] > num[i] > num[i+1], then num[0...i-1] must contains a peak
    // If num[i-1] > num[i] < num[i+1], then both sides have peak
    public static int findPeakElement(int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + ((hi - lo) / 2);
            if (nums[mid] < nums[mid + 1]) {
                lo = mid + 1; // go uphill to the right
            } else {
                hi = mid; // go uphill to the left, don't need -1 since int division rounds down
            }
        }
        return lo;
    }

    // worst case O(n), still passes tests
    public static int findPeakElementSlow(int[] nums) {
        if (nums.length == 1) return 0;
        int lo = 0;
        int hi = nums.length - 1;
        // starts at the ends and works to middle since off the ends counts as negative infinity
        while (lo <= hi) {
            if (nums[lo + 1] < nums[lo]) return lo;
            if (nums[hi - 1] < nums[hi]) return hi;
            lo++;
            hi--;
        }
        return -1;
    }

    // https://leetcode.com/problems/container-with-most-water/
    // O(n) time, O(1) space
    public static int maxArea(int[] height) {
        int result = 0;
        int lo = 0; // two pointers at opposite ends
        int hi = height.length - 1;
        while (lo < hi) {
            int dist = hi - lo;
            int area = Math.min(height[lo], height[hi]) * dist;
            if (area > result) result = area; // saves max area

            if (height[lo] > height[hi]) { // move smaller number pointer closer to middle, may find a larger value
                hi--;
            } else {
                lo++;
            }
        }
        return result;
    }

    // https://leetcode.com/problems/third-maximum-number
    public static int thirdMax(int[] nums) {
        long max = nums[0];
        long max2 = Long.MIN_VALUE; // corner cases are ridiculous if you don't use long
        long max3 = Long.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max3 = max2;
                max2 = max;
                max = nums[i];
            } else if (nums[i] > max2 && nums[i] < max) { // distinct values requires 2nd condition check to avoid when nums[i] = max
                max3 = max2;
                max2 = nums[i];
            } else if (nums[i] > max3 && nums[i] < max2) {
                max3 = nums[i];
            }
        }
        return max3 == Long.MIN_VALUE ? (int) max : (int) max3;
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee
    // O(n) time O(1) space,  a buy and a sell together are charged one transaction fee
    public static int maxProfitFee(int[] prices, int fee) {
        if (prices.length <= 1) return 0;
        int result = 0;
        int possibleBuy = prices[0];
        int potentialProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            // 1st condition is simple: if a lower buy point exists, take it.
            // 2nd condition more involved: if money you'd make now is less than the money you'd have previously made after the
            // transaction fee, you should go back in time and make the previous buy and sell, then re-buy at this nice lower entry point
            if (prices[i] <= possibleBuy || prices[i] - possibleBuy < potentialProfit) {
                //                System.out.printf("Updating potential buy from %d to %d\n", possibleBuy, prices[i]);
                // if there is a profitable saved buy and sell point, perform transaction and rebuy lower
                result += potentialProfit;
                potentialProfit = 0;
                possibleBuy = prices[i]; // possibleBuy not optimal, it should be considered at the lower price
            } else {
                int profit = prices[i] - possibleBuy - fee;
                if (profit > potentialProfit) { // this is good spot to sell, next day may be even better, save it
                    //                    System.out.printf("Updating potential profit from %d to %d\n", potentialProfit, profit);
                    potentialProfit = profit;
                }
            }
        }
        // add the final buy & sell once have all info
        //        System.out.printf("Adding final profit %d\n", potentialProfit);
        return result + potentialProfit;
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
    // greedy algo, single pass
    public static int maxProfit2(int[] prices) {
        int profit = 0;
        int buy = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < buy) {
                buy = prices[i]; // rebuy at lower price
            } else { // price increased
                profit += prices[i] - buy; // sell at higher price
                buy = prices[i]; // rebuy, may continue rising
            }
        }
        return profit;
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
    // greedy algo, single pass
    public static int maxProfit(int[] prices) {
        int profit = 0;
        int buy = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > buy) { // up in value
                profit = Math.max(profit, prices[i] - buy); // if sold today would make a profit
            } else if (prices[i] < buy) { // down in value, could have bought today
                buy = prices[i];
            }
        }
        return profit;
    }

    // https://leetcode.com/problems/next-permutation/
    public static void nextPermutation(int[] n) {
        if (n.length <= 1) return;
        if (n.length == 2) {
            swap(n, 0, 1);
            return;
        }
        int i = n.length - 1;
        for (; i > 0 && n[i - 1] >= n[i]; i--) ;
        if (i == 0) { // n in decreasing order, sort it
            Arrays.sort(n);
        } else { // n[i-1] < n[i] - found the first place to swap
            // replace n[i-1] with the next number greater than itself lying to its right
            int nextGreater = i;
            for (int j = i + 1; j < n.length; j++) {
                if (n[j] < n[nextGreater] && n[j] > n[i - 1]) nextGreater = j;
            }
            swap(n, i - 1, nextGreater);
            // numbers from end in decreasing order, swap above does not change this, need them in increasing order so reverse them
            for (int j = 0; i + j < n.length - 1 - j; j++) {
                swap(n, i + j, n.length - 1 - j);
            }
        }
    }

    private static void swap(int[] n, int a, int b) {
        if (a != b) {
            int temp = n[b];
            n[b] = n[a];
            n[a] = temp;
        }
    }

    // https://leetcode.com/problems/search-insert-position/
    public static int searchInsert(int[] a, int target) {
        int low = 0;
        int high = a.length - 1;
        int mid = 0;
        if (target > a[high]) return a.length;
        if (target < a[low]) return 0;

        while (low <= high) {
            mid = (low + high) / 2; // not appropriate for int overflow
            if (a[mid] == target) {
                return mid;
            } else if (target > a[mid]) {
                if (mid + 1 > high) return mid + 1; // terminates next iteration, a stays sorted, so add above mid
                low = mid + 1;
            } else { // target < a[mid]
                if (mid - 1 < low)
                    return mid; // search terminates next iteration, insert in current spot, so larger values shift to right
                high = mid - 1;
            }
        }
        return mid;
    }

    // given an array with any number of int, there are only two different values, one number only occurs once, find
    // this number that occurs once without using any data structure provided by the language
    public static int uniqueNumber(int[] a) { // a.length >= 3
        // thoughts:
        // 		bucket sort - would need some sort of hash for larger ints - could maybe use mod, but collisions
        // 		save different values and compute array avg, one is closer to avg than the other (only for positive)
        //		save most recent 3 values, when one number doesn't match previous do 3-way compare
        int previousTwo = a[0];
        int previous = a[1];
        if (previous != previousTwo) {
            return threeWayCompare(a[0], a[1], a[2]);
        }
        for (int i = 2; i < a.length; i++) {
            int current = a[i];
            if (current != previous) {
                return threeWayCompare(current, previous, previousTwo);
            }
            previousTwo = previous;
            previous = current;
        }
        return -1;
    }

    private static int threeWayCompare(int a, int b, int c) {
        if (a == b) {
            return c;
        } else if (a == c) {
            return b;
        } else { // b == c
            return a;
        }
    }

    // follow up: array with any number of int, every number occurs twice, except one number that occurs once, find the unique number
    // if the inverviewee come up with nested loop solution ( which is o(n^2) ) ask can they solve it in o(nlogn), can you solve it in o(n) ( with hash map )
    // if the interviewee come up with solution use hashmap, ask can you solve it in constant space, possible answer with with n^2 or nlogn time complexity
    public static int uniqueNumber2(int[] a) { // a.length >= 3
        // sort and traverse = O(nlogn) space and time
        // hashmap counts, traverse array twice
        // nested loop = O(n^2) time and constant space
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            Integer count = counts.get(a[i]);
            if (count == null) {
                counts.put(a[i], 1);
            } else {
                counts.put(a[i], ++count);
            }
        }
        for (int i = 0; i < a.length; i++) {
            if (counts.get(a[i]) != 2) {
                return a[i];
            }
        }
        return -1;
    }

    // follow up: can you solve it in constant space plus o(n) time?
    // 		Yes if input range is limited (i.e. ints 0-9) and use bucket sort or input known to be sorted.
    //		can this be done generally? - yes with XOR: XOR of a number with itself is 0 and with 0 is itself
    public static int uniqueNumber3(int[] a) {
        int r = a[0];
        for (int i = 1; i < a.length; i++) {
            r = r ^ a[i];
        }
        return r;
    }
    // follow up: what if every number occurs three times, except one number (is constant space and o(n) here possible)
    //		Hashmap counts still works, bitwise operators would not since there is an odd number of common occurrences
    //		There is a complicated bitwise solution: https://www.geeksforgeeks.org/find-the-element-that-appears-once/
    // follow up: what if every number occurs k times, except one number (can we generalize this solution)

    // https://leetcode.com/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/
    // non-overlapping makes this tricky, used hints, O(n) runtime to make pre, suf, and get result, O(n) space
    // pre: a.length > 0, no zeros in a, elements of a <= 1000
    public static int minSumOfLengths(int[] a, int target) {
        int[] prefix = new int[a.length]; // prefix[i] is the min length of sub-array ends before i that = target
        int[] suffix = new int[a.length + 1]; // suffix[i] is the min length of sub-array starting at or after i that = target

        prefix[0] = Integer.MAX_VALUE; // prefix[0] never used
        int curSum = a[0]; // current sum of sub array
        int curPos = 0; // starting position of current sub array
        for (int i = 1; i < a.length; i++) {
            if (curSum == target) {
                int length = i - curPos;
                prefix[i] = Math.min(prefix[i - 1], length);
                // start summing a new subarray
                curSum -= a[curPos];
                curPos++;
            } else if (curSum > target) { // start summing a new subarray
                prefix[i] = prefix[i - 1];
                while (curSum >= target) { // find a new starting position for subarray where sum < target
                    curSum -= a[curPos];
                    curPos++;
                    if (curSum == target) {
                        prefix[i] = Math.min(prefix[i - 1], i - curPos);
                    }
                }
            } else { // curSum < target, continue summing a subarray
                prefix[i] = prefix[i - 1];
            }
            curSum += a[i];
        }

        suffix[suffix.length - 1] = Integer.MAX_VALUE; // last suffix position never used
        curSum = 0;
        curPos = a.length - 1;
        for (int i = a.length - 1; i >= 0; i--) {
            curSum += a[i]; // subarray from i to curPos inclusive of both
            if (curSum == target) {
                suffix[i] = Math.min(suffix[i + 1], curPos - i + 1);
                curSum -= a[curPos];
                curPos--;
            } else if (curSum > target) {
                suffix[i] = suffix[i + 1];
                while (curSum >= target) {
                    curSum -= a[curPos];
                    curPos--;
                    if (curSum == target) {
                        suffix[i] = Math.min(suffix[i + 1], curPos - i + 1);
                    }
                }
            } else {
                suffix[i] = suffix[i + 1];
            }
        }

        long result = Integer.MAX_VALUE;
        for (int i = 0; i < prefix.length; i++) {
            result = Math.min(result, (long) prefix[i] + (long) suffix[i]);
        }
        return (int) (result == Integer.MAX_VALUE ? -1 : result);
    }

    public static int minSumOfLengthsBruteFailed(int[] a, int target) {
        SortedMap<Integer, Point> subs = new TreeMap<>();
        for (int i = 0; i < a.length; i++) {
            int sum = a[i];
            int j = i + 1;
            while (sum < target && j < a.length) {
                sum += a[j];
                j++;
            }
            // sum >= target, ignore case where sum exceeded target
            if (sum == target) {
                subs.put(j - i, new Point(i, j)); // save possible subarray, length -> [start,end)
            }
        }
        // sort out the best non-overlapping options
        if (subs.size() < 2) {
            return -1;
        } else {
            Map.Entry<Integer, Point> prev = null;
            for (Map.Entry<Integer, Point> e : subs.entrySet()) {
                if (prev != null && overlaps(prev.getValue(), e.getValue())) {

                }
                prev = e;
            }
        }
        return -1;
    }

    private static boolean overlaps(Point a, Point b) {
        return a.y > b.x;
    }

    public static int minSumOfLengthsGreedyFailed(int[] arr, int target) {
        int[] results = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        int[] sub1 = new int[2]; // start, end of the subarray that sums to results[0]
        int[] sub2 = new int[2]; // start, end of the subarray that sums to results[1]

        int curSum = 0; // current sum of sub array
        int curPos = -1; // 1 before current starting position of sub array
        for (int i = 0; i < arr.length; i++) {
            curSum += arr[i];
            if (curSum == target) {
                // save length of sub array
                int len = i - curPos;
                if (len < results[0]) {
                    results[0] = len;
                } else if (len < results[1]) {
                    results[1] = len;
                }
                // start over
                curSum = 0;
                curPos = i;
            } else if (curSum > target) {
                // start over
                //				curSum -= ;
                //				curPos++;
                //				i = i - X;
            } else {
                // continue
            }
        }
        return -1;
    }

    // Given array a (values between 0 and 9) and number n, return the sum of the n largest elements in a
    //  examples: Input:a={1,2,3} n=2 Output:5    Input:a={2,8,1,3,7,1} n=3 Output:18
    //  Solutions
    //   Use a max heap. Add all elements to heap (log(n)), then pop off N and sum them. n*log(n) runtime, n space.
    //   Sort, then add the N largest. n*log(n) runtime, n space
    //   Bucket sort - use hashmap or array for buckets. O(n) runtime and constant space.
    public static int sumLargest(int[] a, int n) {
        if (n > a.length) return -1;

        int[] buckets = new int[10];
        for (int i = 0; i < a.length; i++) {
            if (a[i] > 9 || a[i] < 0) return -1;
            buckets[a[i]]++;
        }

        int sum = 0;
        for (int i = 9; i > 0; --i) {
            if (buckets[i] < n) {
                sum += buckets[i] * i;
                n -= buckets[i];
            } else {
                sum += n * i;
                return sum;
            }
        }
        return -1; // should not happen
    }

    // Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
    //    The digits are stored such that the most significant digit is at the head of the list,
    //    and each element in the array contain a single digit.
    //    You may assume the integer does not contain any leading zero, except the number 0 itself.
    public static int[] addOne(int[] nums) {
        int i = nums.length - 1;
        while (i >= 0) {
            int last = nums[i];

            if (last != 9) {
                nums[i]++;
                return nums;
            } else {
                nums[i] = 0;
                i--;
            }
        }

        int[] result = new int[nums.length + 1];
        result[0] = 1;
        System.arraycopy(nums, 0, result, 1, nums.length);
        return result;
    }

    // https://leetcode.com/problems/jump-game
    // O(n) time and O(1) space
    public static boolean canJump(int[] nums) {
        int reach = 0; // furthest index we can jump to
        for (int i = 0; i <= reach; i++) {
            int d = i + nums[i]; // index we can jump to from nums[i]
            reach = Math.max(reach, d);
            if (reach >= nums.length - 1) return true;
        }
        return false;
    }

    public static boolean canJumpOG(int[] nums) {
        if (nums.length == 0 || nums.length == 1) return true;
        int jumpPoint = 0;
        while (jumpPoint < nums.length) {
            int start = jumpPoint;
            int end = jumpPoint + nums[jumpPoint];
            int maxPoint = start;
            int maxValue = end;
            //        	System.out.println("jump range: " + start + " - " + end);

            for (int i = jumpPoint; i <= end; i++) {
                if (i >= nums.length - 1) return true;

                if (i + nums[i] > maxValue) {
                    maxPoint = i;
                    maxValue = i + nums[i];
                }
            }
            //        	System.out.println("max point: " + maxPoint);
            if (maxPoint == start) return false; // no progress made, stuck
            jumpPoint = maxPoint;
        }
        return false;
    }

    public static boolean canJumpNaive(int[] nums) {
        if (nums.length == 0) return true;
        return canJumpNaive(nums, 0);
    }

    private static boolean canJumpNaive(int[] nums, int start) {
        if (start == nums.length - 1) return true;
        int jump = nums[start];
        for (int i = start + 1; i <= start + jump; i++) {
            if (canJumpNaive(nums, i)) {
                return true;
            }
        }
        return false;
    }

    // https://leetcode.com/problems/jump-game-ii
    // O(n) time, O(1) space
    public static int jump(int[] nums) {
        if (nums.length <= 1) return 0;
        int jumps = 1;
        int reach = nums[0]; // furthest index we can reach
        int i = 1; // current index
        while (reach < nums.length - 1) { // once we reach nums.length-1 we've won
            // look at the numbers we can reach, perform a jump to the number that gives furthest range
            int max = reach;
            for (; i <= reach; i++) {
                max = Math.max(max, i + nums[i]);
            }
            reach = max; // guaranteed to find something larger since can always reach end
            jumps++;
        }
        return jumps;
    }

    // https://leetcode.com/problems/jump-game-iii/
    public static boolean canReach(int[] arr, int start) {
        boolean[] visited = new boolean[arr.length];
        return canReach(arr, start, visited);
    }

    private static boolean canReach(int[] arr, int start, boolean[] visited) {
        if (start < 0 || start >= arr.length) return false;
        if (arr[start] == 0) return true;
        if (visited[start]) return false;
        visited[start] = true;
        return canReach(arr, start + arr[start], visited) || canReach(arr, start - arr[start], visited);
    }

    // https://leetcode.com/problems/asteroid-collision/
    // O(n+n) = O(n) time and O(n) space, also a brute force solution O(n^2) time and O(1) space
    public static int[] asteroidCollision(int[] asteroids) {
        int n = asteroids.length;
        Stack<Integer> s = new Stack<>();
        s.add(asteroids[0]);
        for (int i = 1; i < n; i++) {
            Integer prev = null;
            if (!s.isEmpty()) prev = s.peek();
            int cur = asteroids[i];
            boolean equal = false;
            // if large asteroid is going to collide with several previous smaller ones, pop them from stack to resolve
            while (prev != null && cur < 0 && prev > 0) {
                // collide
                s.pop(); // remove previous and later add combined
                if (Math.abs(cur) == Math.abs(prev)) {
                    equal = true; // don't add to stack, both go away
                    break;
                } else {
                    cur = Math.abs(cur) > Math.abs(prev) ? cur : prev;
                }
                prev = s.isEmpty() ? null : s.peek();
            }
            if (!equal) s.push(cur);
        }
        // create array from stack
        int[] result = new int[s.size()];
        for (int i = s.size() - 1; i >= 0; i--) {
            result[i] = s.pop();
        }
        return result;
    }

    /* Kth Largest Element in array (also useful for finding median)
     * https://leetcode.com/problems/kth-largest-element-in-an-array/
     * 1. Sort, then take kth index in array. O(N lg N) running time + O(1) memory
     * 2. Priority queue (min queue containing the k largest elements). O(N lg K) running time + O(K) memory
     * 3. Selection Algo (Quickselect): https://en.wikipedia.org/wiki/Selection_algorithm. O(N) running time if input not sorted + O(1) space
     */
    // quick select algorithm - O(n) normal runtime, O(n^2) worst case if input is sorted (can overcome by shuffling)
    public static int findKthLargest(int[] nums, int k) {
        int lo = 0;
        int hi = nums.length - 1;
        int n = partition(nums, lo, hi);
        while (n != nums.length - k) {
            if (n < nums.length - k) { // kth smallest is on the right side
                lo = n + 1;
            } else { // kth smallest is on the left side
                hi = n - 1;
            }
            n = partition(nums, lo, hi);
        }
        return nums[n];
    }

    // picks a pivot value and performs swaps in nums (between lo and hi inclusive) such that numbers < pivot are on
    // left and numbers >= pivot are on the right, right side begins with numbers = pivot, returns the first index of right side
    private static int partition(int[] nums, int lo, int hi) {
        int pivot = nums[lo];
        System.out.printf("Pivot: %d, Lo: %d, Hi: %d\n", pivot, lo, hi);
        int i = lo; // move pivot into position at the end, start at lo+1
        int j = hi + 1; // start at hi
        while (true) {
            while (i < hi && nums[++i] < pivot) ; // i incremented always
            while (j > lo && pivot < nums[--j]) ; // j decremented always
            // nums[i] = hi or is >= pivot,  nums[j] = lo or <= pivot
            if (i >= j) break; // only swap if counters were on appropriate left and right sides of array
            System.out.printf("Nums: %s, swapping %d and %d\n", Arrays.toString(nums), nums[i], nums[j]);
            swap(nums, i, j);
        }
        System.out.printf("Nums: %s, swapping pivot %d and %d\n", Arrays.toString(nums), nums[lo], nums[j]);
        swap(nums, lo, j);
        System.out.println("Nums after partition: " + Arrays.toString(nums));
        System.out.println("Partition Index: " + j);
        return j; // j is smallest now and any values = pivot are on right side, so return j
    }

    // alternative implementation of partition
    private static int partitionAlt(int[] nums, int lo, int hi) {
        int i = lo;
        for (int j = lo + 1; j <= hi; j++) {
            if (nums[j] < nums[lo]) {
                i++;
                System.out.printf("Nums: %s, swapping %d and %d\n", Arrays.toString(nums), i, j);
                swap(nums, i, j);
            }
        }
        System.out.printf("Nums: %s, swapping pivot %d and %d\n", Arrays.toString(nums), lo, i);
        swap(nums, lo, i); // move pivot into place at the end
        System.out.println("Nums after partition: " + Arrays.toString(nums));
        System.out.println("Partition Index: " + i);
        return i;
    }

    public static int findKthLargestHeap(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int val : nums) {
            pq.add(val);
            if (pq.size() > k) pq.remove();
        }
        return pq.peek();
    }

    public static int findKthLargestUsingSorting(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    // https://leetcode.com/problems/rotate-array/ - O(n) time O(1) space
    public static void rotate(int[] a, int k) {
        k = k % a.length;
        reverse(a, a.length - k, a.length - 1); // reverse last section
        reverse(a, 0, a.length - k - 1); // reverse first section
        reverse(a, 0, a.length - 1); // reverse entire array
    }

    private static void reverse(int[] a, int start, int end) {
        while (start < end) {
            int temp = a[start];
            a[start] = a[end];
            a[end] = temp;
            start++;
            end--;
        }
    }

    public static void rotateSimple(int[] a, int k) { // O(n) time and space
        k = k % a.length;
        int[] newA = new int[a.length];
        // index at new starting location and iterate from front of array moving things here
        for (int i = 0; i < a.length; i++) {
            int og = (i + a.length - k) % a.length;
            newA[i] = a[og];
        }
        System.arraycopy(newA, 0, a, 0, a.length);
    }

    // finds the target value in arr in O(lg(n)) time where arr is sorted array w/o duplicates that has been rotated (e.g. 45123)
    public static int indexOfRotated(int[] arr, int target) {
        if (arr.length == 0) return -1;
        if (arr.length == 1) return arr[0] == target ? 0 : -1;
        // find the rotation pivot point
        int low = 0;
        int high = arr.length - 1;
        int highest = -1;
        while (high >= low) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > arr[(mid + 1) % arr.length]) {
                highest = mid;
                break;
            } else if (arr[low] <= arr[mid]) {
                low = mid + 1;
            } else { // arr[low] > arr[mid]
                high = mid;
            }
        }
        if (target >= arr[0]) {
            low = 0;
            high = highest;
        } else {
            low = (highest + 1) % arr.length;
            high = arr.length - 1;
        }
        while (high >= low) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else { // arr[mid] > key
                high = mid - 1;
            }
        }
        return -1;
    }

    public static int indexOfBinarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (high >= low) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else { // arr[mid] > key
                high = mid - 1;
            }
        }
        return -1;
    }
    public static int indexOfBinarySearchRecursive(int[] arr, int target) {
        return indexOfBinarySearchRecursive(arr, 0, arr.length-1, target);
    }
    private static int indexOfBinarySearchRecursive(int[] arr, int l, int r, int x) {
        if (r >= l) {
            int mid = l + (r-l) / 2;
            if (arr[mid] == x)
                return mid;
            if (arr[mid] > x)
                return indexOfBinarySearchRecursive(arr, l, mid-1, x);
            return indexOfBinarySearchRecursive(arr, mid+1, r, x);
        }
        return -1;
    }

    // find the missing k numbers in sequence: http://stackoverflow.com/questions/3492302/easy-interview-question-got-harder-given-numbers-1-100-find-the-missing-numbe
    // - sort the sequence and iterate through identifying differences between consecutive numbers that are greater than 1
    // - calculate the sum of the kth powers of all the numbers that should be in the sequence and compare to the sum of the kth powers of the actual numbers in the
    //    sequence, solve a system of k equations with k unknowns for the values.

    // pre: sequence contains a single missing number and contains the unordered sequence of numbers from 1 to sequence.length
    // returns the missing number in sequence - O(n) time, O(1) space, works even with integer overflow
    public static int missingNumber(Object[] sequence) {
        int completeSeqLength = sequence.length + 1;
        int completeSeqSum = completeSeqLength * (completeSeqLength + 1) / 2; // math identity: sum of 1...n = n*(n+1)/2
        int sum = 0;
        for (int i = 0; i < sequence.length; i++) sum += (int) sequence[i];
        return completeSeqSum - sum;
    }

    // sequence contains two missing numbers and contains the unordered sequence of numbers from 1 to sequence.length
    public static int[] missingNumbers(Object[] sequence) {
        //			int completeSeqLength = sequence.length + 2;
        //			int completeSeqSum = completeSeqLength*(completeSeqLength+1)/2; // math identity: sum of 1...n = n*(n+1)/2
        //			int completeSeqSquareSum = completeSeqLength*(completeSeqLength+1)*(2*completeSeqLength + 1)/6; // math identity: sum of 1^2...n^2 = n*(n+1)*(2n+1)/6
        //			int sum = 0;
        //			int squareSum = 0;
        //			int i;
        //			for(i = 0; i < sequence.length; i++) {
        //				int n = (int)sequence[i];
        //				sum += n;
        //				squareSum += n*n;
        //				//completeSeqSquareSum += (i+1) * (i+1); // do this if do not know the formula to directly calculate sum of n squares
        //			}
        //			//completeSeqSquareSum += (i+1) * (i+1);
        //
        //			// solve 2 eqns w/ 2 unknowns
        //			int missingNumSum = completeSeqSum - sum; // = x1 + x2
        //			int missingNumProduct = completeSeqSquareSum - squareSum; // = x1^2 + x2^2
        //
        //			// PROBLEM
        //			int x1 = (int) Math.sqrt((missingNumProduct + 2*missingNumSum - missingNumSum * missingNumSum) / 2.0);
        //
        //			return new int[] {missingNumSum, missingNumProduct, x1};

        int Sum = 0;
        int SumN = 0;
        BigInteger P = new BigInteger("1");
        BigInteger Np = new BigInteger("1");
        int a, b;
        int range = sequence.length + 2;
        SumN = range * (range + 1) / 2;
        for (int i = 0; i < sequence.length; i++) {
            Sum += (int) sequence[i];
        }
        int s = SumN - Sum;
        int i = 0;
        for (; i < sequence.length; i++) {
            P = P.multiply(new BigInteger(sequence[i].toString()));
            Np = Np.multiply(new BigInteger(Integer.toString(i + 1)));
        }
        Np = Np.multiply(new BigInteger(Integer.toString(i++)));
        Np = Np.multiply(new BigInteger(Integer.toString(i++)));
        int product = Np.divide(P).intValue();
        int diffSqr = (int) Math.sqrt(s * s - 4 * product); // (a-b)^2 = (a+b)^2-4ab
        a = (s + diffSqr) / 2;
        b = s - a;
        int[] result = {a, b};
        return result;
    }


    // returns the first two indexes in the given array that add up to the given target - O(n) time and space
    public static int[] twoSum(int[] nums, int target) {
        // https://leetcode.com/articles/two-sum/
        // only needs one pass since upon finding the second value that forms the complement, we return
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i); // map values to indexes
        }
        return null;
    }

    // https://leetcode.com/problems/3sum/
    // O(n logn + n^2) = O(n^2) time, O(1) space if sort done in place
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        Set<List<Integer>> result = new HashSet<>(); // using set to avoid duplicates
        for (int i = 0; i < nums.length - 2; i++) {
            int lo = i + 1;
            int hi = nums.length - 1;
            while (lo < hi) {
                if (nums[i] + nums[lo] + nums[lo] > 0) break; // optimization: hi can never be larger than lo

                int sum = nums[i] + nums[lo] + nums[hi];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                    lo++;
                } else if (sum > 0) {
                    hi--;
                } else {
                    lo++;
                }
            }
        }
        return new ArrayList<>(result);
    }

    // doesn't work since saves sums of pairs and then looks at all numbers, so a number may sum with itself in results
    // tried saving sums of pairs and the index of pair members, that fails on input like {0,0}
    public static List<List<Integer>> threeSumWrong(int[] nums) {
        // find all pairings of two and their sum; map sum->[pair]
        Map<Integer, List<int[]>> sums = new HashMap<>();
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int sum = nums[i] + nums[j];
                List<int[]> pairs = sums.get(sum);
                if (pairs == null) {
                    pairs = new ArrayList<>();
                }
                pairs.add(new int[]{nums[i], nums[j]});
                sums.put(sum, pairs);
            }
        }
        Set<List<Integer>> result = new HashSet<>(); // using set to avoid duplicates
        for (int num : nums) {
            if (sums.containsKey(-num)) {
                List<int[]> pairs = sums.get(-num);
                for (int[] pair : pairs) {
                    List<Integer> triplet = Arrays.asList(pair[0], pair[1], num);
                    Collections.sort(triplet);
                    result.add(triplet);
                }
            }
        }
        return new ArrayList<>(result);
    }

    // returns first triplet of indexes in the given array that add up to the given target - O(n^3) time O(1) space
    public static int[] firstThreeSumN3(int[] nums, int target) {
        for (int i = 0; i < nums.length-2; i++) {
            for (int j = i + 1; j < nums.length-1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == target) {
                        return new int[]{i, j, k};
                    }
                }
            }
        }
        return null;
    }
    public static int[] firstThreeSumN2(int[] nums, int target) { // requires nums to be sorted
        for (int i = 0; i < nums.length - 2; i++) {
            int l = i + 1; // index of the first element in the remaining elements
            int r = nums.length - 1; // index of the last element
            while (l < r) {
                if (nums[i] + nums[l] + nums[r] == target)
                    return new int[]{i, l, r};
                else if (nums[i] + nums[l] + nums[r] < target)
                    l++;
                else // A[i] + A[l] + A[r] > sum
                    r--;
            }
        }
        return null;
    }

    // https://leetcode.com/problems/4sum
    // O(nlogn + n^3) time, O(n) extra space (this would be needed for result anyway though)
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> result = new HashSet<>();
        for (int a = 0; a < nums.length - 3; a++) {
            for (int b = a + 1; b < nums.length - 2; b++) {
                int lo = b + 1;
                int hi = nums.length - 1;
                while (lo < hi) {
                    if (nums[a] + nums[b] + 2 * nums[lo] > target)
                        break; // smaller val must be <= avg and greater >= avg

                    int sum = nums[a] + nums[b] + nums[lo] + nums[hi];
                    if (sum < target) {
                        lo++;
                    } else if (sum > target) {
                        hi--;
                    } else {
                        List<Integer> entry = Arrays.asList(nums[a], nums[b], nums[lo], nums[hi]);
                        Collections.sort(entry);
                        result.add(entry);
                        lo++;
                    }
                }
            }
        }
        return new ArrayList<>(result);
    }

    public static List<List<Integer>> fourSumBrute(int[] nums, int target) {
        Set<List<Integer>> result = new HashSet<>();
        for (int a = 0; a < nums.length - 3; a++) {
            for (int b = a + 1; b < nums.length - 2; b++) {
                for (int c = b + 1; c < nums.length - 1; c++) {
                    for (int d = c + 1; d < nums.length; d++) {
                        if (nums[a] + nums[b] + nums[c] + nums[d] == target) {
                            List<Integer> entry = Arrays.asList(nums[a], nums[b], nums[c], nums[d]);
                            Collections.sort(entry);
                            result.add(entry);
                        }
                    }
                }
            }
        }
        return new ArrayList<>(result);
    }

    // https://leetcode.com/problems/4sum-ii/
    public static int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        // O(n^2) time and space by saving all possible sums of arrays 1 and 2, then 3 and 4
        int n = nums1.length;
        Map<Integer, Integer> counts1and2 = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                counts1and2.merge(nums1[i] + nums2[j], 1, Integer::sum);
            }
        }
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int sum = nums3[i] + nums4[j];
                Integer k = counts1and2.get(-sum); // -sum is what it takes to add to zero
                if (k != null) result += k;
            }
        }
        return result;
    }

    public static int fourSumCountSlow(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        // obvious n^4 nested loops, O(1) space
        // also n^3 with nested loops + hashmap, O(n) space, as is implemented here
        int result = 0;
        Map<Integer, Integer> counts = new HashMap<>(nums4.length);
        for (int n : nums4) {
            counts.merge(n, 1, Integer::sum);
        }
        for (int a : nums1) {
            for (int b : nums2) {
                for (int c : nums3) {
                    int sum = a + b + c;
                    Integer count = counts.get(-sum);
                    if (count != null) {
                        result += count;
                    }
                }
            }
        }
        return result;
    }

    // Fisher Yates shuffle: https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
    public static void shuffle(int[] arr) {
        Random r = new Random();
        for (int i = arr.length - 1; i >= 1; i--) {
            int rand = r.nextInt(i + 1); // random element from the original (unshuffled) part of the array
            // swap
            int temp = arr[i];
            arr[i] = arr[rand]; // put the chosen element at the end of the original part of the array, building the shuffled part of the array from back to front
            arr[rand] = temp; // maintain the shrinking unshuffled part of the array at the front, with the swap
        }
    }

    //	Given an array nums and a value val, remove all instances of that value in-place and return the new length.
    //	Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
    //	The order of elements can be changed. It doesn't matter what you leave beyond the new length.
    //	Given nums = [3,2,2,3], val = 3,
    //	Your function should return length = 2, with the first two elements of nums being 2.
    public int removeElement(int[] nums, int val) {
        int end = nums.length - 1;
        int sum = 0;
        for (int i = 0; i < nums.length && i <= end; i++) {
            if (nums[i] == val) {
                nums[i] = nums[end];
                nums[end] = val;
                end--;
                i--;
            } else {
                sum++;
            }
        }
        return sum;
    }

    // https://leetcode.com/problems/plus-one/
    public static int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) return digits;
        int i = digits.length - 1;
        while (i >= 0 && digits[i] == 9) i--;
        if (i == -1) { // entire array is 9s
            int[] result = new int[digits.length + 1];
            result[0] = 1;
            return result;
        }
        digits[i]++;
        // go back and reset the 9s to 0s
        for (i++; i < digits.length; i++) {
            digits[i] = 0;
        }
        return digits;
    }

    // https://leetcode.com/problems/maximum-product-subarray/discuss/
    // Find the contiguous subarray within an array (containing at least one number) which has the largest product.
    public static int maxProduct(int[] nums) { // ingenious O(n) time
        // keep track of the previous max (and min since product of 2 negatives = positive)
        int max = nums[0];
        int prevMax = nums[0];
        int prevMin = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int curMax = Math.max(nums[i], Math.max(prevMin * nums[i], prevMax * nums[i]));
            int curMin = Math.min(nums[i], Math.min(prevMin * nums[i], prevMax * nums[i]));
            max = Math.max(max, curMax);
            prevMax = curMax;
            prevMin = curMin;
        }
        return max;
    }
    public static int maxProductSlow(int[] nums) { // simple O(n^2) time
        int max = nums[0];
        for (int i = 0; i < nums.length; i++) { // possible subarray start points
            max = Math.max(max, nums[i]); // single item could be max
            int prod = nums[i];
            for (int j = i+1; j < nums.length; j++) { // possible subarray end points
                prod *= nums[j];
                max = Math.max(max, prod);
            }
        }
        return max;
    }
}
