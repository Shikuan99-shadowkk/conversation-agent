import sqlite3

conn = sqlite3.connect('healthcare_database') 
c = conn.cursor()

c.execute('''
          CREATE TABLE IF NOT EXISTS clients
          ([client_id] INTEGER PRIMARY KEY AUTOINCREMENT, [client_name] TEXT, [age] INTEGER, [sex] TEXT, 
          [weight] INTEGER, [height] INTEGER, [exercise] BOOLEAN, 
          [temperature] DOUBLE, [pain_score] DOUBLE, [emotion_score] DOUBLE)
          ''')

c.execute('''
          CREATE TABLE IF NOT EXISTS symptoms
          ([symptom_id] INTEGER PRIMARY KEY, [symptom_name] TEXT, [symptom_type] TEXT)
          ''')

c.execute('''
          CREATE TABLE IF NOT EXISTS allergens
          ([allergen_id] INTEGER PRIMARY KEY, [allergen_name] TEXT)
          ''')

c.execute('''
          CREATE TABLE IF NOT EXISTS patients
          ([patient_id] INTEGER PRIMARY KEY, [client_id] INTEGER, [symptom_id] INTEGER, [recommendation] TEXT, [start_time] DATE)
          ''')

c.execute('''
          CREATE TABLE IF NOT EXISTS allergies
          ([allergy_id] INTEGER PRIMARY KEY, [client_id] INTEGER, [allergen_id] INTEGER)
          ''')

c.execute('''
          INSERT INTO symptoms (symptom_id, symptom_name, symptom_type)

                VALUES
                (NULL,'fever','main'),
                (NULL,'sore throat','minor'),
                (NULL,'dry eyes','main'),
                (NULL,'dizziness','minor'),
                (NULL,'back pain','main'),
                (NULL,'muscle ache','minor')
          ''')

c.execute('''
          INSERT INTO allergens (allergen_id, allergen_name)

                VALUES
                (NULL,'penicillin'),
                (NULL,'aspirin'),
                (NULL,'ibuprofen')
          ''')
                     
conn.commit()