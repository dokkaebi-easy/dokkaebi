import React from 'react';
import Box from '@mui/material/Box';
import AddIcon from '@mui/icons-material/Add';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Divider from '@mui/material/Divider';
import Paper from '@mui/material/Paper';
import { v4 as uuid } from 'uuid';
import BuildBasic from 'Components/Pages/Setting/BuildPage/BuildBasicBox/BuildBasicBox';
import BuildData from 'Components/MDClass/BuildData/BuildData';
import { useStore } from 'Components/Store/SettingStore/SettingStore';
import NginxBasic from './NginxBasic/NginxBasic';

export default function BuildPage() {
  const projectName = useStore((state) => state.projectName);
  const setProjectName = useStore((state) => state.setProjectName);

  const buildConfigs = useStore((state) => state.buildConfigs);
  const setBuildConfigs = useStore((state) => state.setBuildConfigs);
  const nginxConfig = useStore((state) => state.nginxConfig);
  const setNginxConfig = useStore((state) => state.setNginxConfig);

  const handlePropsDelClick = (index: number) => {
    buildConfigs.splice(index, 1);
    setBuildConfigs(buildConfigs);
  };

  const handleChange = (event: any) => {
    setProjectName(event.target.value);
  };

  const handleAddClick = () => {
    const data = new BuildData();
    setBuildConfigs([...buildConfigs, data]);
  };

  return (
    <Box>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper sx={{ padding: 1, textAlign: 'center', width: 200 }}>
          <Typography variant="h5">Build Setting</Typography>
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3, pt: 4 }}>
          <Typography>Project Name</Typography>
          <TextField
            onChange={handleChange}
            id="outlined-basic"
            label="Project Name"
            variant="outlined"
            size="small"
            sx={{ my: 2 }}
            placeholder="ProjectName"
            defaultValue={projectName}
          />
          <Divider />
          <Box sx={{ my: 3 }}>
            {buildConfigs.map((value, index) => (
              <BuildBasic
                key={uuid()}
                index={index}
                value={value}
                DelClick={handlePropsDelClick}
              />
            ))}
            <Box my={3} sx={{ display: 'flex', justifyContent: 'end' }}>
              <Button
                onClick={handleAddClick}
                variant="outlined"
                startIcon={<AddIcon />}
              >
                Add
              </Button>
            </Box>
            <Divider />
            <Box mt={3}>
              <NginxBasic nginxValue={nginxConfig} />
            </Box>
          </Box>
        </Paper>
      </Box>
    </Box>
  );
}
