CREATE DATABASE petfoster_2.0
GO
use petfoster_2.0
GO
	INSERT [dbo].[pet_type] (type_id,type_name) 	
	 VALUES (N'B001', N'Bird'),
		(N'C001', N'Cat'),
		(N'D001', N'Dog'),
		(N'M001', N'Mouse')

	INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'AC001', N'Abyssinian cat', N'C001')
 ,(N'BC001', N'Bengal cat ', N'C001')
, (N'BL001', N'British longhair', N'C001')
, (N'BS001', N'British shorthair ', N'C001')
,(N'C001', N'Chihuahua', N'D001')
,(N'CCD001', N'Chinese Chongqing Dog', N'D001')
,(N'EM001', N'Egyptian mau ', N'C001')
,(N'GS001', N'German Shepherd', N'D001')
, (N'JC001', N'Japanese Chin', N'D001')
,(N'KD001', N'Kangal Dog', N'D001')
,(N'MC001', N'Maine Coon', N'C001')
,(N'ND001', N'Native dog', N'D001'),
(N'PQR001', N'Phu Quoc Ridgeback', N'D001'),
(N'SC001', N'Siamese cat ', N'C001'),
(N'SC002', N'Siberian cat', N'C001'),
 (N'SF001', N'Scottish Fold ', N'C001'),
 (N'SI001', N'Shiba inu', N'D001'),
 (N'TB001', N'Tabby cat', N'C001'),
 (N'TV001', N'Turkish Van', N'C001')



 INSERT [dbo].[product_type] ([product_type_id], [product_type_name]) 
 VALUES (N'BF', N'Bird food'),
		(N'CF', N'Cat food'), 
		(N'DF', N'Dog food'),
		(N'MF', N'Mouse food'),
		(N'RF', N'Rabbit Food')



SET IDENTITY_INSERT [dbo].[brand] ON 
INSERT [dbo].[brand] ([id], [brand]) VALUES (1, N'Me-O')
,(2, N'Minino Yum')
,(3, N'Whiskas')
,(4, N'Nutrience Original Healthy Adult Indoor')
,(5, N'Cat’s Eye')
,(6, N'Home & Cat')
,(7, N'Iskhanq')
,(8, N'Nutri Source')
,(9, N'Royal Canin')
,(10, N'CatsRang')
,(11, N'Zenith Puppy')
,(12, N'Smartheart')
,(13, N'Ganador')
,(14, N'Classic Pets')
SET IDENTITY_INSERT [dbo].[brand] OFF
GO

INSERT [dbo].[product] ([product_id], [brand_id], [create_at], [product_desc], [is_active], [product_name], [type_id]) VALUES 
(N'PD0001', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'ME-O Tuna In Jelly', N'CF')
,(N'PD0002', 1, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'The Pet FRESH Pate For Cats With Loss Of Appetite', N'CF')
,(N'PD0003', 1, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Nekko Jelly Cat Pate', N'CF')
,(N'PD0004', 2, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Pate Whiskas Junior For Kittens', N'CF')
,(N'PD0005', 3, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Snappy Cat Tom Real Fish Pate', N'CF')
,(N'PD0006', 4, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Snappy Tom Premium Pate', N'CF')
,(N'PD0007', 1, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Zenith Adult Soft Pellets For Adult Dogs', N'DF')
,(N'PD0008', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Royal Canin X-Small Adult Dog', N'DF')
,(N'PD0009', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Salmon Flavor', N'CF')
,(N'PD0010', 2, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Natural Organic Dog Food For All Ages', N'DF')
,(N'PD0011', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Kitten Ocean Fish', N'CF')
,(N'PD0012', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Cat Mackerel Flavor', N'CF')
,(N'PD0013', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Creamy Treat Chicken Flavor', N'CF')
,(N'PD0014', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Creamy Treat Crab Flavor', N'CF')
,(N'PD0015', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Creamy Treat Tuna Flavor', N'CF')
,(N'PD0016', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Creamy Treat Bonito Flavor', N'CF')
,(N'PD0017', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Gold Fit & Firm', N'CF')
,(N'PD0018', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Cat Litter Lemon Scent', N'CF')
,(N'PD0019', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Delite Tuna In Jelly', N'CF')
,(N'PD0020', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Royal Canin Indoor Adult Dry Cat Food', N'CF')
,(N'PD0021', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Apro I.Q Formula', N'DF')
,(N'PD0022', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Zoi Dog', N'DF')
,(N'PD0023', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Creamy Chicken & Spinach', N'DF')
,(N'PD0024', 3, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Nutri Pate For Poodles Of All Ages', N'DF')
,(N'PD0025', 3, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Chicken Pate For Large Dogs', N'DF')
,(N'PD0026', 3, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Fish Pate For All Dogs', N'DF')
,(N'PD0027', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Smartheart Puppy Power Pack', N'DF')
,(N'PD0028', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Smartheart Adult Roast Beef Flavour', N'DF')
,(N'PD0029', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Smartheart Small Breeds Roast Beef Flavor', N'DF')
,(N'PD0030', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Smartheart Puppy Chicken Flavor Chunk In Gravy', N'DF')
,(N'PD0031', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Royal Canin Hypoallergenic Dog Pate 400g', N'DF')
,(N'PD0032', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Feedy', N'DF')
,(N'PD0033', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Altair', N'DF')
,(N'PD0034', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Royal Canin Poodle Adult Dog Food', N'DF')
,(N'PD0035', 9, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Royal Canin Maxi Adult Large Dog Food', N'DF')
,(N'PD0036', 3, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Fresh Meat New Model - CAT', N'CF')
,(N'PD0037', 1, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Snappy Cat Tom Tuna Mixed Fruit Pate', N'CF')
,(N'PD0038', 1, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Pedigree Adult Mini Small Breed Adult Dog Food', N'DF')
,(N'PD0039',1, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Natural Organic Brown Rice, Sweet Potatoes', N'DF')
,(N'PD0040', 1, CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Natural Salmon Organic', N'DF')
GO


SET IDENTITY_INSERT [dbo].[product_repo] ON
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (1, 140000, 1, 200000, 20, 500, N'PD0001' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (2, 140000, 1, 200000, 20, 1000, N'PD0001' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (3, 70000, 1, 100000, 50, 500, N'PD0002' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (4, 10000, 1, 20000, 100, 70, N'PD0003' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (5, 30000, 1, 45000, 70, 100, N'PD0004' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (6, 50000, 1, 80000, 50, 400, N'PD0005' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (7, 20000, 1, 35000, 100, 100, N'PD0006' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (8, 45000, 1, 70000, 20, 300, N'PD0007' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (9, 14000, 1, 160000, 50, 500, N'PD0008' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (10, 130000, 1, 170000, 100, 200, N'PD0009' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (11, 500000, 1, 600000, 20, 5000, N'PD0010' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (12, 200000, 1, 250000, 70, 1000, N'PD0011' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (13, 500000, 1, 600000, 20, 200, N'PD0012' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (14, 130000, 1, 170000, 20, 1000, N'PD0013' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (15, 500000, 1, 600000, 50, 1000, N'PD0014' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (16, 130000, 1, 170000, 20, 400, N'PD0015' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (17, 200000, 1, 250000, 20, 1000, N'PD0016' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (18, 45000, 1, 75000, 70, 1000, N'PD0017' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (19, 200000, 1, 250000, 50, 400, N'PD0018' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (20, 25000, 1, 50000, 100, 200, N'PD0019' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (21, 130000, 1, 150000, 20, 1000, N'PD0020' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (22, 45000, 1, 75000, 20, 300, N'PD0021' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (23, 25000, 1, 50000, 100, 200, N'PD0022' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (24, 200000, 1, 250000, 20, 1000, N'PD0023' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (25, 25000, 1, 50000, 100, 200, N'PD0024' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (26, 45000, 1, 90000, 100, 1000, N'PD0025' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (27, 200000, 1, 250000, 20, 200, N'PD0026' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (28, 130000, 1, 150000, 70, 1000, N'PD0027' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (29, 200000, 1, 250000, 100, 200, N'PD0028' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (30, 45000, 1, 90000, 20, 1000, N'PD0029' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (31, 25000, 1, 50000, 100, 1000, N'PD0030' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (32, 120000, 1, 140000, 50, 400, N'PD0031' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (33, 130000, 1, 200000, 100, 200, N'PD0032' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (34, 45000, 1, 70000, 100, 200, N'PD0033' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (35, 190000, 1, 230000, 70, 200, N'PD0034' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (36, 35000, 1, 50000, 180, 250, N'PD0001' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (37, 70000, 1, 100000, 100, 500, N'PD0001' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (38, 160000, 1, 200000, 34, 500, N'PD0035' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (39, 320000, 1, 400000, 73, 1000, N'PD0035' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (40, 390000, 1, 560000, 72, 1200, N'PD0035' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (41, 70000, 1, 90000, 83, 500, N'PD0036' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (42, 105000, 1, 135000, 17, 750, N'PD0036' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (43, 15000, 1, 25000, 90, 70, N'PD0037' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (44, 40000, 1, 60000, 100, 400, N'PD0038' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (45, 230000, 1, 280000, 35, 1000, N'PD0039' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (46, 60000, 1, 75000, 40, 250, N'PD0040' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (47, 120000, 1, 150000, 50, 500, N'PD0040' , 1)
INSERT [dbo].[product_repo] ([product_repo_id], [In_price], [in_stock], [out_price], [quantity], [size], [product_id], [is_active])  VALUES (48, 240000, 1, 300000, 67, 1000, N'PD0040' , 1)
SET IDENTITY_INSERT [dbo].[product_repo] OFF
GO


SET IDENTITY_INSERT [dbo].[delivery_company] ON 
INSERT [dbo].[delivery_company] ([id],[company])
	VALUES(1, N'Giao hàng nhanh'),
		(2, N'Giao hàng tiết kiệm')
SET IDENTITY_INSERT [dbo].[delivery_company] OFF 
GO





SET IDENTITY_INSERT [dbo].[Imgs] ON 

INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (41, N'imgproduct0001.jpg', NULL, N'PD0001')
,(42, N'imgproduct0002.jpg', NULL, N'PD0002')
,(43, N'imgproduct0003.jpg', NULL, N'PD0003')
,(44, N'imgproduct0004.jpg', NULL, N'PD0004')
,(45, N'imgproduct0005.jpg', NULL, N'PD0005')
,(46, N'imgproduct0006.jpg', NULL, N'PD0006')
,(47, N'imgproduct0007.jpg', NULL, N'PD0007')
,(48, N'imgproduct0008.jpg', NULL, N'PD0008')
,(49, N'imgproduct0009.jpg', NULL, N'PD0009')
,(50, N'imgproduct0010.jpg', NULL, N'PD0010')
,(51, N'imgproduct0011.jpg', NULL, N'PD0011')
,(52, N'imgproduct0012.jpg', NULL, N'PD0012')
,(53, N'imgproduct0013.jpg', NULL, N'PD0013')
,(54, N'imgproduct0014.jpg', NULL, N'PD0014')
,(55, N'imgproduct0015.jpg', NULL, N'PD0015')
,(56, N'imgproduct0016.jpg', NULL, N'PD0016')
,(57, N'imgproduct0017.jpg', NULL, N'PD0017')
,(58, N'imgproduct0018.jpg', NULL, N'PD0018')
,(59, N'imgproduct0019.jpg', NULL, N'PD0019')
,(60, N'imgproduct0020.jpg', NULL, N'PD0020')
,(61, N'imgproduct0021.jpg', NULL, N'PD0021')
,(62, N'imgproduct0022.jpg', NULL, N'PD0022')
,(63, N'imgproduct0023.jpg', NULL, N'PD0023')
,(64, N'imgproduct0024.jpg', NULL, N'PD0024')
,(65, N'imgproduct0025.jpg', NULL, N'PD0025')
,(66, N'imgproduct0026.jpg', NULL, N'PD0026')
,(67, N'imgproduct0027.jpg', NULL, N'PD0027')
,(68, N'imgproduct0028.jpg', NULL, N'PD0028')
,(69, N'imgproduct0029.jpg', NULL, N'PD0029')
,(70, N'imgproduct0030.jpg', NULL, N'PD0030')
,(71, N'imgproduct0031.jpg', NULL, N'PD0031')
,(72, N'imgproduct0032.jpg', NULL, N'PD0032')
,(73, N'imgproduct0033.jpg', NULL, N'PD0033')
,(74, N'imgproduct0034.jpg', NULL, N'PD0034')
,(75, N'imgproduct0035.jpg', NULL, N'PD0035')
,(76, N'imgproduct0036.jpg', NULL, N'PD0036')
,(77, N'imgproduct0037.jpg', NULL, N'PD0037')
,(78, N'imgproduct0038.jpg', NULL, N'PD0038')
,(79, N'imgproduct0039.jpg', NULL, N'PD0039')
,(80, N'imgproduct0040.jpg', NULL, N'PD0040')
SET IDENTITY_INSERT [dbo].[Imgs] OFF
GO

INSERT INTO [dbo].[users]
           ([user_id]
           ,[avatar]
           ,[birthday]
           ,[create_at]
           ,[email]
           ,[fullname]
           ,[gender]
           ,[is_active]
           ,[is_email_verified]
           ,[password]
           ,[phone]
           ,[token]
           ,[token_create_at]
           ,[username])
     VALUES
			(N'superadmin',N'avatar0001.jpg'
		   ,CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2)
		   ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
		   ,N'superadmin@gmail.com',N'Super Admin'
           ,0,1,1
           ,N'passnaylachuoi32kytuduocmahoa',N'0912543876'
           ,N'chonaydeghitoken'
           ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
           ,N'superadmin'),
			(N'admin',N'avatar0001.jpg'
		   ,CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2)
		   ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
		   ,N'admin@gmail.com',N'Admin 1'
           ,0,1,1
           ,N'passnaylachuoi32kytuduocmahoa',N'0912543876'
           ,N'chonaydeghitoken'
           ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
           ,N'admin'),

		   (N'staff001',N'avatar0001.jpg'
		   ,CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2)
		   ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
		   ,N'staff001@gmail.com',N'Staff 001'
           ,0,1,1
           ,N'passnaylachuoi32kytuduocmahoa',N'0912543876'
           ,N'chonaydeghitoken'
           ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
           ,N'staff001'),

		   
		   (N'staff002',N'avatar0001.jpg'
		   ,CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2)
		   ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
		   ,N'staff002@gmail.com',N'Staff 001'
           ,0,1,1
           ,N'passnaylachuoi32kytuduocmahoa',N'0912543876'
           ,N'chonaydeghitoken'
           ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
           ,N'staff002'),

           (N'userid00001',N'avatar0001.jpg'
		   ,CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2)
		   ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
		   ,N'userid0001@gmail.com',N'Nguyen Thi Hong'
           ,0,1,1
           ,N'passnaylachuoi32kytuduocmahoa',N'0912543876'
           ,N'chonaydeghitoken'
           ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
           ,N'khach001'),

		   (N'userid00002',N'avatar0002.jpg'
		   ,CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2)
		   ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
		   ,N'userid0002@gmail.com',N'Nguyen Van Viet'
           ,0,1,1
           ,N'passnaylachuoi32kytuduocmahoa',N'0912543876'
           ,N'chonaydeghitoken'
           ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
           ,N'khach002'),

		   (N'userid00003',N'avatar0003.jpg'
		   ,CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2)
		   ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
		   ,N'userid0001@gmail.com',N'Ho Tung Mau'
           ,0,1,1
           ,N'passnaylachuoi32kytuduocmahoa',N'0912543876'
           ,N'chonaydeghitoken'
           ,CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2)
           ,N'khach003')		   
GO


INSERT INTO [dbo].[search_history]
           ([keyword]
           ,[search_at]
           ,[user_id])
     VALUES
           (N'Cat eye',CAST(N'2023-02-05T00:00:00.0000000' AS DateTime2),N'userid00003'),
           (N'CatsRang',CAST(N'2023-01-02T00:00:00.0000000' AS DateTime2),N'userid00002'),
           (N'Home & Cat',CAST(N'2023-01-03T00:00:00.0000000' AS DateTime2),N'userid00002'),
           (N'Royal Canin',CAST(N'2023-02-05T00:00:00.0000000' AS DateTime2),N'userid00001'),
           (N'Me-O',CAST(N'2023-02-05T00:00:00.0000000' AS DateTime2),N'userid00003'),
           (N'Whiskas',CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2),N'userid00003'),
           (N'Whistcas',CAST(N'2023-01-06T00:00:00.0000000' AS DateTime2),N'userid00003')
GO

INSERT INTO [dbo].[role]
           ([role]
           ,[role_desc])
     VALUES
           (N'ROLE_SUPER_ADMIN',N'Super Admin'),
		   (N'ROLE_ADMIN',N'Admin'),
           (N'ROLE_STAFF',N'Staff'),
           (N'ROLE_USER',N'User')
GO

INSERT INTO [dbo].[authorities]
           ([role_id]
           ,[user_id])
     VALUES
		   (1,N'superadmin'),
		   (2,N'admin'),
		   (3,N'staff001'),
		   (3,N'staff002'),
		   (2,N'staff002'),
           (4,N'userid00001'),
		   (4,N'userid00002'),
		   (4,N'userid00003')
		  
GO


INSERT INTO [dbo].[recent_view]
           ([view_at]
           ,[product_id]
           ,[user_id])
     VALUES
           (CAST(N'2023-08-10T00:00:00.0000000' AS DateTime2),N'PD0001',N'userid00001'),
		   (CAST(N'2023-08-11T00:00:00.0000000' AS DateTime2),N'PD0011',N'userid00001'),
		   (CAST(N'2023-08-12T00:00:00.0000000' AS DateTime2),N'PD0013',N'userid00001'),
		   (CAST(N'2023-08-13T00:00:00.0000000' AS DateTime2),N'PD0023',N'userid00001'),
		   (CAST(N'2023-08-11T00:00:00.0000000' AS DateTime2),N'PD0027',N'userid00002'),
		   (CAST(N'2023-08-12T00:00:00.0000000' AS DateTime2),N'PD0018',N'userid00002'),
		   (CAST(N'2023-08-13T00:00:00.0000000' AS DateTime2),N'PD0017',N'userid00002'),
		   (CAST(N'2023-08-14T00:00:00.0000000' AS DateTime2),N'PD0003',N'userid00002'),
		   (CAST(N'2023-08-11T00:00:00.0000000' AS DateTime2),N'PD0006',N'userid00003'),
		   (CAST(N'2023-08-12T00:00:00.0000000' AS DateTime2),N'PD0009',N'userid00003'),
		   (CAST(N'2023-08-08T00:00:00.0000000' AS DateTime2),N'PD0016',N'userid00003'),
		   (CAST(N'2023-08-02T00:00:00.0000000' AS DateTime2),N'PD0021',N'userid00003'),
		   (CAST(N'2023-08-03T00:00:00.0000000' AS DateTime2),N'PD0023',N'userid00003'),
		   (CAST(N'2023-08-04T00:00:00.0000000' AS DateTime2),N'PD0031',N'userid00003'),
		   (CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2),N'PD0011',N'userid00003')
GO


INSERT INTO [dbo].[addresses]
           ([is_default]
           ,[address]
           ,[create_at]
           ,[district]
           ,[phone]
           ,[province]
           ,[recipient]
           ,[ward]
           ,[user_id])
     VALUES
           (1,N'Số 85-87 Trần Hưng Đạo',CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2),N'Hoàn Kiếm',N'0911771155',N'TP. Hà Nội',N'Huỳnh Văn Tuân',N'Chương Dương',N'userid00001'),
		   (0,N'Số 17 Hồ Thị Kỷ',CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2),N'Hoàn Kiếm',N'0911771155',N'TP. Hà Nội',N'Huỳnh Văn Tuân',N'Chương Dương',N'userid00001'),
		   (1,N'Số 268 Trần Hưng Đạo',CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2),N'Q.1',N'0907193185',N'TP. HCM',N'Trần Văn Việt',N'P. Nguyễn Cư Trinh',N'userid00002'),
		   (0,N'Số 02 Lê Đại Hành',CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2),N'Q.1',N'0907193185',N'TP. HCM',N'Trần Văn Việt',N'P. Nguyễn Cư Trinh',N'userid00002'),
		   (1,N'Số 18 Lê Hồng Phong',CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2),N'Ninh Kiều',N'0939702086',N'TP. Hà Nội',N'Hồ Minh Tâm',N'P. Cái Khế,',N'userid00003'),
		   (0,N'Số 9A Trần Phú',CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2),N'Ninh Kiều',N'0939702086',N'TP. Hà Nội',N'Hồ Minh Tâm',N'P. Cái Khế,',N'userid00003')
GO

INSERT INTO [dbo].[carts]
           ([user_id])
     VALUES
           (N'userid00001'),
		   (N'userid00002'),
		   (N'userid00003')
GO

INSERT INTO [dbo].[cart_item]
           ([quantity]
           ,[cart_id]
           ,[product_repo_id])
     VALUES
           (2,1,12),
		   (1,1,15),
		   (1,1,21),
		   (5,1,12),
		   (1,1,16),
		   (2,1,31),
		   (2,2,21),
		   (1,2,11),
		   (3,2,33),
		   (1,2,45),
		   (9,2,47),
		   (2,2,25),
		   (2,3,21),
		   (1,3,41),
		   (3,3,42),
		   (1,3,43)		  
GO

INSERT INTO [dbo].[payment_method]
           ([method])
     VALUES
           (N'Cash'),
		   (N'Banking')
GO


SET IDENTITY_INSERT [dbo].[shipping_info] ON 
INSERT [dbo].[shipping_info] ([id], [address], [district], [full_name], [phone], [province], [ship_fee], [ward], [delivery_company_id])
	VALUES (1, N'135 Trần Hưng Đạo',N'Ninh Kiều', N'Pham Nhut Khang', N'0966456789',N'Cần Thơ', 30000,N'Phường 3',1)
			,(2, N'135 Trần Hưng Đạo',N'Ninh Kiều', N'Pham Nhut Khang', N'0966456789',N'Cần Thơ', 30000,N'Phường 3',2)			
			,(3, N'135 Trần Hưng Đạo',N'Ninh Kiều', N'Phạm Trung Đức', N'0966456789',N'Cần Thơ', 30000,N'Phường 5',1)
			,(4, N'135 Trần Hưng Đạo',N'Ninh Kiều', N'Phạm Trung Đức', N'0966456789',N'Cần Thơ', 30000,N'Phường 5',2)
			,(5, N'135 Trần Hưng Đạo',N'Ninh Kiều', N'Nguyễn Quốc Duy', N'0966456789',N'Cần Thơ', 30000,N'Phường 1',1)
			,(6, N'135 Trần Hưng Đạo',N'Ninh Kiều', N'Nguyễn Quốc Duy', N'0966456789',N'Cần Thơ', 30000,N'Phường 1',2)
			,(7, N'135 Trần Hưng Đạo',N'Ninh Kiều', N'Ngô Thị Hồng Hạnh', N'0966456789',N'Cần Thơ', 30000,N'Phường 3',1)
			,(8, N'135 Trần Hưng Đạo',N'Ninh Kiều', N'Nguyễn Minh Anh', N'0966456789',N'Cần Thơ', 30000,N'Phường 3',2)
			,(9, N'135 Trần Hưng Đạo',N'Ninh Kiều', N'Trần Văn Việt', N'0966456789',N'Cần Thơ', 30000,N'Phường 3',1)	
SET IDENTITY_INSERT [dbo].[shipping_info] OFF
GO



SET IDENTITY_INSERT [dbo].[payment] ON
INSERT INTO [dbo].[payment]
           ([id],[amount],[is_paid],[pay_at],[transaction_number],[payment_method_id])
     VALUES
           (1,230000,1,CAST(N'2023-08-10T00:00:00.0000000' AS DateTime2),N'14165018',2),
		   (2,530000,1,CAST(N'2023-08-20T00:00:00.0000000' AS DateTime2),N'',1),
		   (3,630000,0,Null,N'',1)

SET IDENTITY_INSERT [dbo].[payment] OFF
GO


SET IDENTITY_INSERT [dbo].[orders] ON

INSERT INTO [dbo].[orders]
           ([id],[create_at],[descriptions],[status],[total],[payment_id],[shipping_info_id],[user_id])
     VALUES
           (1,CAST(N'2023-08-10T00:00:00.0000000' AS DateTime2),N'descriptions',N'Đang chờ lấy hàng',230000,1,1,N'userid00003'),
		   (2,CAST(N'2023-08-19T00:00:00.0000000' AS DateTime2),N'descriptions',N'Đã nhận hàng',530000,1,2,N'userid00003'),
		   (3,CAST(N'2023-08-20T00:00:00.0000000' AS DateTime2),N'descriptions',N'Đang giao hàng',630000,1,3,N'userid00003')

GO
SET IDENTITY_INSERT [dbo].[orders] OFF


INSERT INTO [dbo].[order_detail]
           ([price],[quantity],[total],[order_id],[product_repo_id])
     VALUES
           (50000,1,50000,1,25),
		   (150000,1,150000,1,47),

		   (35000,10,350000,2,7),
		   (150000,1,150000,2,21),

		    (60000,5,300000,3,44),
		   (300000,1,300000,3,48)
GO


INSERT INTO [dbo].[review]
           ([comment]
           ,[create_at]
           ,[rate]
           ,[order_id]
           ,[product_id]
           ,[user_id])
     VALUES
           (N'Sản Phẩm tốt',CAST(N'2023-08-19T00:00:00.0000000' AS DateTime2),4,2,N'PD0006',N'userid00003'),
		   (N'Sản Phẩm tốt giá rẻ',CAST(N'2023-08-19T00:00:00.0000000' AS DateTime2),5,2,N'PD0020',N'userid00003')

GO

INSERT [dbo].[pet_type] ([type_id], [type_name]) VALUES (N'B001', N'Bird')
INSERT [dbo].[pet_type] ([type_id], [type_name]) VALUES (N'C001', N'Cat')
INSERT [dbo].[pet_type] ([type_id], [type_name]) VALUES (N'D001', N'Dog')
INSERT [dbo].[pet_type] ([type_id], [type_name]) VALUES (N'M001', N'Mouse')
GO
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'AC001', N'Abyssinian cat', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'BC001', N'Bengal cat ', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'BL001', N'British longhair', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'BS001', N'British shorthair ', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'C001', N'Chihuahua', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'CCD001', N'Chinese Chongqing Dog', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'EM001', N'Egyptian mau ', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'GS001', N'German Shepherd', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'JC001', N'Japanese Chin', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'KD001', N'Kangal Dog', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'MC001', N'Maine Coon', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'ND001', N'Native dog', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'PQR001', N'Phu Quoc Ridgeback', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'SC001', N'Siamese cat ', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'SC002', N'Siberian cat', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'SF001', N'Scottish Fold ', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'SI001', N'Shiba inu', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'TB001', N'Tabby cat', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'TV001', N'Turkish Van', N'C001')
GO





