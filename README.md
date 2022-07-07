#   Manacher's algorithm (longest palindromic string)

Here I just cover algotithm basic idea for future myself (and, perhaps, another fellow cs student).
Thanks a lot to [this guy]( https://www.youtube.com/watch?v=SV1ZaKCozS4 ). But you can really just read through this README to grasp the idea.

Basic principle here is we get some string on input, call is **s**. Next, say **n** = s.length(). So we create auxiliary char array named **sChar** with length equals to 2n+1. For concrete example, let's pick A string "abaaba", then sChar is "#a#b#a#a#b#a#", length are 8 and 17 respectively.

| string       | # | a | # | b | # | a | # | a | # | b | #  | a  | #  |
|--------------|---|---|---|---|---|---|---|---|---|---|----|----|----|
| LPS length L | 0 | 1 | 0 | 3 | 0 | 1 | 6 | 1 | 0 | 3 | 0  | 1  | 0  |
| Position i   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 |


So we may conclude the following - if position **i** is odd, then it's L is also odd and not lesser then 1 (since char itself is palindromic). On the other hand, even postions have even L and starts with 0.

So don't mistook _postion_ and _index_. Index is a usual index of string ( in range 0 through n - 1 ). And postion is what we got on modified version of string ( in range 0 through 2n );

We can address LPS two ways: either in term of indexs or in terms of postions.

 - substring from position of _i - d_ to _i + d_ is a palindrom of length d (in terms of postion)
 - substring form index _(i - d) / 2_ to _(i + d) / 2 - 1_ is a palindrom of length d (in terms of index)

To clarify this: L[3] = 3 means that substring from position _0 ( 3 - 3 )_ to _6 ( 3 + 3 )_ is a palindrom of length _3_, this is also means that from index _0 (( 3 - 3 ) / 2)_ to _2 ( 3 + 3) / 2 -1_ is a palindrom.

So this algorithm takes advantage of mirror nature of palindroms in the string ( L 0 to 6 position and mirror image of 6 to 12 ).


Let's look at core optimeze catches that do this algorithm faster then regular approach. take as an example "abababa" string and make it's "hash" version - "#a#b#a#b#a#b#a".

| string       | # | a | # | b | # | a | # | b | # | a | #  | b  | #  | a  | #  |
|--------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|
| LPS length L | 0 | 1 | 0 | 3 | 0 | 5 | 0 | 7 | 0 | 5 | 0  | 3  | 0  | 1  | 0  |
| Position i   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 |
| Bounds       | l |   |   |   |   |   |   |   |   |   |    |    |    |    | r  |

As you can see here position 7 is a center of a palindrom here. And since the whole word is palindrom, we may know LPS for 8 through 14 knowing left side of mirror (see the table above to prove it). It's intuitive and leave you to prove this by yourself. And this example has it's left bound and right bound and shown above ( size of palindrom ).

But when we have string like "abababab" or "babababa" thing will be a little different. Not mirror copy-paste LSP didnot work and we may say only about precision.

| string       | # | a | # | b | # | a | # | b | # | a | #  | b  | #  | a  | #  | b  | #  |
|--------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|
| LPS length L | 0 | 1 | 0 | 3 | 0 | 5 | 0 | 7 |   |   |    |    |    |    |    |    |    |
| Position i   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 |
| Bounds       | l |   |   |   |   |   |   |   |   |   |    |    |    | r  |    |    |    |

As you can see now computed to 7th position we need to know the rest since new update - appended letter b ( and a # with it ). Look closer at position 8 - we can have **not less** then five - that is, a is 5 because it's left side is restricted with 0 index, while 8 postion is not. The same rule we can apply to others and got 


| string       | # | a | # | b | # | a | # | b | # | a    | #  | b    | #  | a    | #  | b  | #  |
|--------------|---|---|---|---|---|---|---|---|---|------|----|------|----|------|----|----|----|
| LPS length L | 0 | 1 | 0 | 3 | 0 | 5 | 0 | 7 | 0 | >= 5 | 0  | >= 3 | 0  | >= 1 | 0  | 1  | 0  |
| Position i   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9    | 10 | 11   | 12 | 13   | 14 | 15 | 16 |
| Bounds       | l |   |   |   |   |   |   |   |   |      |    |      |    | r    |    |    |    |


The last two position area easily derive from the key logic, so we need only to give precision to 9, 11, and 13 postions. For 8 position take 2 left and 2 indexes right (that is, we already know it is at leat 5 length) and check for bigger palindrom. Same logic apply to all unknown postions. Result: 


| string       | # | a | # | b | # | a | # | b | # | a | #  | b  | #  | a  | #  | b  | #  |
|--------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|
| LPS length L | 0 | 1 | 0 | 3 | 0 | 5 | 0 | 7 | 0 | 7 | 0  | 5  | 0  | 3  | 0  | 1  | 0  |
| Position i   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 |
| Bounds       | l |   |   |   |   |   |   |   |   |   |    |    |    | r  |    |    |    |

So you also follow the same logic with "babababa" string - but in this case 10, 12 and 14 positions will decrease since left side is expanded.
Here's what you got:

| string       | # | b | # | a | # | b | # | a | # | b | #  | a  | #  | b  | #  | a  | #  |
|--------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|
| LPS length L | 0 | 1 | 0 | 3 | 0 | 5 | 0 | 7 | 0 | 7 | 0  | 5  | 0  | 3  | 0  | 1  | 0  |
| Position i   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 |
| Bounds       |   |   |   | l |   |   |   |   |   |   |    |    |    |    |    |    | r  |

That is a core conspect of Manacher's algorithm. I'll take one example for you to see it in action. But from now on you may skip and dive deep into coded implemetion.
First of all, we need update this center on iterations. Key takeaway in this algorithm wil be:
    

1. if current postion is not out R bound, then we can use the mirror to deremine this.
2. expand around the center
3. if current exeeded the center, then update center to current

Follow along and we will have desired result. I will show here some of iterations

| pointer(i) |    | i |   |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |
|------------|----|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|----|----|----|----|----|----|
| positions  | 0  | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 |
| string     | #  | a | # | b | # | a | # | x | # | a | #  | b  | #  | a  | #  | x  | #  | a  | #  | b  | #  | b  | #  |
| bounds     | LR |   |   |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |
| L          |    |   |   |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |


We start from here. Since out i is out of bound, we cannot use mirror to obtain it's L ( step 1 ). This means that we will now fully expand around the center. Here we can expand once - to 0 and 2 positions.
Next we put 1 to L[1] ( step 2 ). Then we need to check if we can update the center - since we exeeded R bound ( with length of ith palindrom, not just ith postion itself ), then we put a new center and the right bound ( step 3 ).


| pointer(i) |   |   | i |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |
|------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|----|----|----|----|----|----|
| positions  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 |
| string     | # | a | # | b | # | a | # | x | # | a | #  | b  | #  | a  | #  | x  | #  | a  | #  | b  | #  | b  | #  |
| bounds     | L | C | R |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |
| L          | 0 | 1 |   |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |


i = 2 ( here we can update value with mirror - do not forget that you need to expand even if you used your mirror ):


| pointer(i) |   |   |   | i |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |
|------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|----|----|----|----|----|----|
| positions  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 |
| string     | # | a | # | b | # | a | # | x | # | a | #  | b  | #  | a  | #  | x  | #  | a  | #  | b  | #  | b  | #  |
| bounds     | L | C | R |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |
| L          | 0 | 1 | 0 |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |



i = 3 ( once again - update R bound and the center ):

| pointer(i) |   |   |   |   | i |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |
|------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|----|----|----|----|----|----|
| positions  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 |
| string     | # | a | # | b | # | a | # | x | # | a | #  | b  | #  | a  | #  | x  | #  | a  | #  | b  | #  | b  | #  |
| bounds     | L |   |   | C |   | R |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |
| L          | 0 | 1 | 0 | 3 |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |


i = 6 ( pointer is expend R bound, so we cannot use the mirror, but here we do not update bounds and center ):

| pointer(i) |   |   |   |   |   |   |   | i |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |
|------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|----|----|----|----|----|----|
| positions  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 |
| string     | # | a | # | b | # | a | # | x | # | a | #  | b  | #  | a  | #  | x  | #  | a  | #  | b  | #  | b  | #  |
| bounds     | L |   |   | C |   |   | R |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |
| L          | 0 | 1 | 0 | 3 | 0 | 1 | 0 |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    |    |


i = 11 ( here we expand beyond R bound, so to update it ( and so center and left bound ) on the third step ):

| pointer(i) |   |   |   |   |   |   |   |   |   |   |    |    | i  |    |    |    |    |    |    |    |    |    |    |
|------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|----|----|----|----|----|----|
| positions  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 |
| string     | # | a | # | b | # | a | # | x | # | a | #  | b  | #  | a  | #  | x  | #  | a  | #  | b  | #  | b  | #  |
| bounds     |   |   | L |   |   |   |   |   |   |   |    | C  |    |    |    |    |    |    |    |    | R  |    |    |
| L          | 0 | 1 | 0 | 3 | 0 | 1 | 0 | 7 | 0 | 1 | 0  | 9  |    |    |    |    |    |    |    |    |    |    |    |


i = 15 ( here we mirror value of 7, but size to right is 5, so step 1 give us only 5, then we try to expand on the second step, and that's not possible, note we do not update the boundaries ):

| pointer(i) |   |   |   |   |   |   |   |   |   |   |    |    |    |    |    |    | i  |    |    |    |    |    |    |
|------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|----|----|----|----|----|----|
| positions  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 |
| string     | # | a | # | b | # | a | # | x | # | a | #  | b  | #  | a  | #  | x  | #  | a  | #  | b  | #  | b  | #  |
| bounds     |   |   | L |   |   |   |   |   |   |   |    | C  |    |    |    |    |    |    |    |    | R  |    |    |
| L          | 0 | 1 | 0 | 3 | 0 | 1 | 0 | 7 | 0 | 1 | 0  | 9  | 0  | 1  | 0  | 5  |    |    |    |    |    |    |    |


i = 20 -> prefinal result ( note that bound became tighter ):

| pointer(i) |   |   |   |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    | i  |    |
|------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|----|----|----|----|----|----|
| positions  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 |
| string     | # | a | # | b | # | a | # | x | # | a | #  | b  | #  | a  | #  | x  | #  | a  | #  | b  | #  | b  | #  |
| bounds     |   |   |   |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    | L  |    | C  |    | R  |
| L          | 0 | 1 | 0 | 3 | 0 | 1 | 0 | 7 | 0 | 1 | 0  | 9  | 0  | 1  | 0  | 5  | 0  | 1  | 0  | 1  | 2  |    |    |


Final result;

| pointer(i) |   |   |   |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    |    |    |    |    | i  |
|------------|---|---|---|---|---|---|---|---|---|---|----|----|----|----|----|----|----|----|----|----|----|----|----|
| positions  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 |
| string     | # | a | # | b | # | a | # | x | # | a | #  | b  | #  | a  | #  | x  | #  | a  | #  | b  | #  | b  | #  |
| bounds     |   |   |   |   |   |   |   |   |   |   |    |    |    |    |    |    |    |    | L  |    | C  |    | R  |
| L          | 0 | 1 | 0 | 3 | 0 | 1 | 0 | 7 | 0 | 1 | 0  | 9  | 0  | 1  | 0  | 5  | 0  | 1  | 0  | 1  | 2  | 1  | 0  |


Note: there is a version with 2*n + 3 string length with $ @ around. This is used to go around some special charaters and escape sequences.

Try to code it yourself before looking at my code, it's really fun! Perhapse you can do it cleaner ;}

---

Time Complexity: O(n)

Space Complexity: O(n)

The more you know!
![Alt text](But_It's_Honest_Work.jpg)


---
######  Read, rewrite, DWTFYW with this
