import { useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import PropertyData, {
  Property,
} from 'Components/MDClass/PropertyData/PropertyData';
import { Build } from 'Components/MDClass/BuildData/BuildData';
import { v4 as uuid } from 'uuid';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
import Stack from '@mui/material/Stack';
import PropertyBox from '../PropertyBox/PropertyBox';

interface PropertyProps {
  buildValue: Build;
}

export default function BuildPropertyBox({ buildValue }: PropertyProps) {
  const [hidden, setHidden] = useState(true);

  const [propertyDatas, setPropertyDatas] = useState<Property[]>(
    buildValue.properties,
  );

  const handleHiddenClick = () => {
    setHidden((cur) => !cur);
  };
  const handleOnClick = () => {
    const newData = new PropertyData();
    setPropertyDatas((cur) => [...cur, newData]);
    buildValue.properties = [...buildValue.properties, new PropertyData()];
  };

  const handleDelClickProps = (index: number) => {
    buildValue.properties.splice(index, 1);
    setPropertyDatas([...buildValue.properties]);
  };

  return (
    <Box my={3}>
      <Box position="relative" sx={{ top: 10, left: 10 }}>
        <Paper
          sx={{
            padding: 1,
            textAlign: 'center',
            width: 120,
            color: ' white',
            background: 'linear-gradient(195deg, #666, #191919)',
          }}
        >
          Property
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3 }}>
          <Stack direction="row" spacing={2}>
            <Box mb={3}>
              <Button
                variant="outlined"
                startIcon={<AddIcon />}
                onClick={handleOnClick}
                sx={{ color: 'black', borderColor: 'black' }}
              >
                Property Add
              </Button>
            </Box>
            <Box mb={3}>
              <Button
                variant="outlined"
                onClick={handleHiddenClick}
                startIcon={hidden ? <ArrowDropUpIcon /> : <ArrowDropDownIcon />}
                sx={{ color: 'black', borderColor: 'black' }}
              >
                Property Hidden
              </Button>
            </Box>
          </Stack>
          {hidden ? null : (
            <>
              {propertyDatas.map((value, index) => {
                return (
                  <PropertyBox
                    key={uuid()}
                    value={value}
                    buildValue={buildValue.properties[index]}
                    index={index}
                    DelClick={handleDelClickProps}
                  />
                );
              })}
            </>
          )}
        </Paper>
      </Box>
    </Box>
  );
}
