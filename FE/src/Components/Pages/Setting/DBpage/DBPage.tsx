import Box from '@mui/material/Box';
import AddIcon from '@mui/icons-material/Add';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import { v4 as uuid } from 'uuid';
import DBData from 'Components/MDClass/DBData/DBData';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';
import DBPaper from './DBPaper/DBPaper';

export default function DBpage() {
  const dbConfigs = useSettingStore((state) => state.dbConfigs);
  const setDBConfigs = useSettingStore((state) => state.setDBConfigs);

  const handlePropsDelClick = (index: number) => {
    dbConfigs.splice(index, 1);
    setDBConfigs([...dbConfigs]);
  };

  const handleAddClick = () => {
    const data = new DBData();
    setDBConfigs([...dbConfigs, data]);
  };

  return (
    <Box>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper
          sx={{
            padding: 1,
            textAlign: 'center',
            width: 200,
            color: ' white',
            background: 'linear-gradient(195deg, #666, #191919)',
          }}
        >
          <Typography variant="h5">DB Setting</Typography>
        </Paper>
      </Box>
      <Box>
        <Paper
          sx={{ padding: 3, pt: 4, borderWidth: 3 }}
          elevation={0}
          variant="outlined"
        >
          <Box mt={3} sx={{ display: 'flex' }}>
            <Button
              onClick={handleAddClick}
              variant="outlined"
              startIcon={<AddIcon />}
              sx={{ marginRight: 3 }}
            >
              DB 추가
            </Button>
          </Box>
          <Box sx={{ my: 3 }}>
            {dbConfigs.map((value, index) => (
              <DBPaper
                key={uuid()}
                index={index}
                dbData={value}
                DelClick={handlePropsDelClick}
              />
            ))}
          </Box>
        </Paper>
      </Box>
    </Box>
  );
}
