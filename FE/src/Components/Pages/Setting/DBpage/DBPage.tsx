import Box from '@mui/material/Box';
import AddIcon from '@mui/icons-material/Add';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import { v4 as uuid } from 'uuid';
import BuildData from 'Components/MDClass/BuildData/BuildData';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';
import DBPaper from './DBPaper/DBPaper';

export default function DBpage() {
  const buildConfigs = useSettingStore((state) => state.buildConfigs);
  const setBuildConfigs = useSettingStore((state) => state.setBuildConfigs);

  const handlePropsDelClick = (index: number) => {
    buildConfigs.splice(index, 1);
    setBuildConfigs([...buildConfigs]);
  };

  const handleAddClick = () => {
    const data = new BuildData();
    setBuildConfigs([...buildConfigs, data]);
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
        <Paper sx={{ padding: 3, pt: 4 }}>
          <Box mt={3} sx={{ display: 'flex' }}>
            <Button
              onClick={handleAddClick}
              variant="outlined"
              startIcon={<AddIcon />}
              sx={{ marginRight: 3, color: 'black', borderColor: 'black' }}
            >
              DB Add
            </Button>
          </Box>
          <Box sx={{ my: 3 }}>
            {buildConfigs.map((value, index) => (
              <DBPaper
                key={uuid()}
                index={index}
                buildData={value}
                DelClick={handlePropsDelClick}
              />
            ))}
          </Box>
        </Paper>
      </Box>
    </Box>
  );
}
