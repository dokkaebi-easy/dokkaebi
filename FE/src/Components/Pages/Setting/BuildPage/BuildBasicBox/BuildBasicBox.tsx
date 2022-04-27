import React, { useEffect, useState } from 'react';
import Grid from '@mui/material/Grid';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import FormHelperText from '@mui/material/FormHelperText';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { Build } from 'Components/MDClass/BuildData/BuildData';
import Paper from '@mui/material/Paper';
import axios from 'axios';
import ResponseIdNameData, {
  ResponseIdName,
} from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';
import { useStore } from 'Components/Store/DropDownStore/DropDownStore';
import BuildProperty from '../BuildPropertyBox/BuildPropertyBox';

interface VersionTypeAxois {
  buildTool: string[];
  frameworkVersion: string[];
}

interface buildProps {
  index: number;
  value: Build;
  DelClick: (value: number) => void;
}

export default function BuildBasicBox({ index, value, DelClick }: buildProps) {
  const framAndLibsdata = useStore((state) => state.framworkandLib);
  const setFramAndLibsdata = useStore((state) => state.setFramworkandLib);

  const [name, setName] = useState(value.name);
  const [fileDir, setFileDir] = useState(value.projectDirectory);
  const [buildPath, setBuildPath] = useState(value.buildPath);
  const [frameworkName, setFrameworkName] = useState(value.frameworkName);
  const [frameworkId, setFrameworkId] = useState(-1);
  const [version, setVersion] = useState(value.version);
  const [type, setType] = useState(value.type);

  const [framAndLibs, setFramAndLibs] = useState<string[]>([
    value.frameworkName,
  ]);
  const [versions, setVersions] = useState<string[]>([value.version]);
  const [types, setTypes] = useState<string[]>([value.type]);

  const handleNameOnChange = (event: any) => {
    setName(event.target.value);
    value.name = event.target.value;
  };

  const handleFileDirOnChange = (event: any) => {
    setFileDir(event.target.value);
    value.projectDirectory = event.target.value;
  };
  const handlebuildPathOnChange = (event: any) => {
    setBuildPath(event.target.value);
    value.buildPath = event.target.value;
  };

  const handleDelOnClick = () => {
    DelClick(index);
  };

  const handlePropsFLChange = (event: string) => {
    setFrameworkName(event);
    value.frameworkName = event;
    value.version = '';
    value.type = '';
  };

  const handlePropsVersionChange = (event: string) => {
    setVersion(event);
    value.version = event;
  };
  const handlePropsTypeChange = (event: string) => {
    setType(event);
    value.type = event;
  };
  const handleItemClickProps = (index: number) => {
    setFrameworkName(framAndLibsdata[index].name);

    const params = { typeId: framAndLibsdata[index].id };
    axios
      .get('/api/project/frameworkVersion', { params })
      .then((res) => {
        const data = res.data as VersionTypeAxois;
        console.log(data);
        setVersion('');
        setType('');

        setVersions(data.frameworkVersion);
        setTypes(data.buildTool);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    axios
      .get('/api/project/frameworkType')
      .then((res) => {
        const data = res.data as ResponseIdName[];
        setFramAndLibsdata(data);
        const arr = data.map((value) => {
          if (frameworkName === value.name) setFrameworkId(value.id);
          return value.name;
        });
        setFramAndLibs([...arr]);
      })
      .catch((err) => {
        console.log(err);
      });

    if (frameworkId !== -1) {
      const params = { typeId: framAndLibsdata[index].id };
      axios
        .get('/api/project/frameworkVersion', { params })
        .then((res) => {
          const data = res.data as VersionTypeAxois;
          setVersions(data.frameworkVersion);
          setTypes(data.buildTool);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, []);

  return (
    <Box>
      <Paper sx={{ padding: 3 }}>
        <Grid container spacing={2}>
          <Grid item>
            <Typography>Name</Typography>
            <TextField
              defaultValue={name}
              id="outlined-basic"
              label="Name"
              variant="outlined"
              size="small"
              sx={{ my: 1 }}
              placeholder="Name"
              onChange={handleNameOnChange}
            />
          </Grid>
          <Grid item>
            <Typography>Framework/ Library</Typography>
            <SelectItem
              defaultValue={frameworkName}
              label="Framework/ Library"
              Items={framAndLibs}
              Change={handlePropsFLChange}
              Click={handleItemClickProps}
            />
          </Grid>
          <Grid item>
            <Typography>Version</Typography>
            <SelectItem
              defaultValue={version}
              label="Versions"
              Items={versions}
              Change={handlePropsVersionChange}
            />
          </Grid>
          <Grid item>
            <Typography>
              {frameworkName === 'Vue' || frameworkName === 'React'
                ? 'Nginx Use'
                : 'Type'}
            </Typography>
            <SelectItem
              defaultValue={type}
              label="Types"
              Items={types}
              Change={handlePropsTypeChange}
            />
            {frameworkName === 'Vue' || frameworkName === 'React' ? (
              <FormHelperText id="component-helper-text">
                (※ Yes는 하나만)
              </FormHelperText>
            ) : (
              <Box />
            )}
          </Grid>
          <Grid item />
          <Grid item>
            <Typography>File Dir</Typography>
            <TextField
              id="outlined-basic"
              label="Dir"
              variant="outlined"
              size="small"
              sx={{ my: 1 }}
              placeholder="Dir"
              defaultValue={fileDir}
              onChange={handleFileDirOnChange}
            />
          </Grid>
          <Grid item>
            <Typography>Build Path</Typography>
            <TextField
              id="outlined-basic"
              label="Dir"
              variant="outlined"
              size="small"
              sx={{ my: 1 }}
              placeholder="Dir"
              defaultValue={buildPath}
              onChange={handlebuildPathOnChange}
            />
          </Grid>
        </Grid>
        <Stack direction="row" spacing={2}>
          <Button
            variant="outlined"
            startIcon={<ArrowDropDownIcon />}
            size="small"
          >
            Property
          </Button>
          <Button
            variant="outlined"
            startIcon={<DeleteIcon />}
            size="small"
            onClick={handleDelOnClick}
          >
            Delete
          </Button>
        </Stack>
        <BuildProperty buildValue={value} />
      </Paper>
    </Box>
  );
}
