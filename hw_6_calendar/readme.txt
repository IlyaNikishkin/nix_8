Программа работает с 4-мя форматами:
dd/mm/yy - 01/12/08
m/d/yyyy - 3/4/0521
mmm-d-yy - Март-4-21
dd-mmm-yyyy - 09-Апрель-0789
Блок времени может быть добавлен к дате в любой момент к любому формату после пробела как 00:00, 00:00:00 или 00:00:00:000.
Разделителями в форматах даты выступает "-" или "/", а для блока времени ":".
Если значение между разделителями не указано, программа понимает это как нижний предел этого значения - например,
"//" преобразует в 1 янв 0-го г. 0ч0м0с0мс при выборе формата "dd/mm/yy" или "m/d/yyyy".
Верхний предел календаря ограничен типом long (java) или 9 223 372 036 854 775 807 мс, или примерно 292 млн. лет.
Также максимальное число мс/сек/мин/дн, которое можно прибавить/отнять к дате, ограничено типом long (java).